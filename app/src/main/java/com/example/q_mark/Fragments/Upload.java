package com.example.q_mark.Fragments;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.ConnectionReceiver;
import com.example.q_mark.Home;
import com.example.q_mark.Model.Files;
import com.example.q_mark.Model.User;
import com.example.q_mark.R;
import com.example.q_mark.databinding.UploadBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class Upload extends Fragment {

    RecyclerView recyclerView;
    TextView noFilesText;

    private String curPath;
    UploadBinding binding;
    ActivityResultLauncher<String> launcher;
    Uri uri;
    URL url;
    private String unv,type;

    String datatype;

    BroadcastReceiver broadcastReceiver;

    //firebase objects
    FirebaseAuth mauth;
    DatabaseReference firebaseDatabase;
    FirebaseStorage firebaseStorage;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = UploadBinding.inflate(inflater,container,false);
        binding.chsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("*/*");

            }
        });
        binding.slide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type="Slide";
                binding.others.setChecked(false);
                binding.pdf.setChecked(false);
            }
        });
       binding.pdf.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               type="Question";
               binding.others.setChecked(false);
               binding.slide.setChecked(false);
           }
       });
       binding.others.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               type="Others";
               binding.pdf.setChecked(false);
               binding.slide.setChecked(false);
           }
       });



        launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result!=null)
                {
                    binding.chsbtn.setText(result.getPath().toString());
                    uri=result;
                }
            }
        });

        FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user=snapshot.getValue(User.class);
                                unv=user.getUniversity();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
        binding.upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                broadcastReceiver = new ConnectionReceiver();
                registorNetworkBroadcast();

                Files files=new Files();

                files.setName(binding.fname.getText().toString());
                files.setUniversity(unv);
                files.setCourse(binding.fcourse.getText().toString());
                files.setYear(binding.fyear.getText().toString());
                files.setSubject(binding.fsubject.getText().toString());
                files.setHint(binding.fname.getText().toString()+"*"+binding.fcourse.getText().toString()+"*"+
                                binding.fsubject.getText().toString()+"*"+
                                unv);






                files.setUploaderid(FirebaseAuth.getInstance().getUid());


                if((binding.slide.isChecked() || binding.pdf.isChecked() || binding.others.isChecked()) && binding.fname.getText()!=null &&
                        binding.fname.getText().length()!=0 && binding.fcourse.getText()!=null && binding.fcourse.getText().length()!=0 &&
                        binding.fyear.getText()!=null && binding.fyear.getText().length()!=0 && binding.fsubject.getText()!=null && binding.fsubject.getText().length()!=0
                        && uri!=null)
                {
                    ProgressDialog progressBar = new ProgressDialog(view.getContext());
                    progressBar.setCancelable(true);
                    progressBar.setMessage("File uploading ...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                    progressBar.show();

                    files.setType(type);
                    StorageReference reference=FirebaseStorage.getInstance().getReference().child("Upload/"+ UUID.randomUUID().toString());
                    reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri urii) {

                                    String ttt= taskSnapshot.getMetadata().getContentType();
                                    ttt=ttt.toLowerCase().substring(ttt.toLowerCase().lastIndexOf(".")+1);
                                    files.setFiletype(ttt);
                                    files.setPath(urii.toString());
                                    String key=FirebaseDatabase.getInstance().getReference().child("Upload").push().getKey();
                                    Map<String, Object> postValues = files.toMap();
                                    Map<String, Object> childUpdates = new HashMap<>();
                                    childUpdates.put("/Upload/" + key, postValues);
                                    childUpdates.put("/Upload_unv/"+files.getUniversity()+"/"+key,postValues);
                                    FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            //dismiss progressbar
                                            progressBar.dismiss();
                                            Toast.makeText(getActivity(), "File uploaded successfully.", Toast.LENGTH_SHORT).show();
                                            Fragment frnd_fragment=new Home();
                                            FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                                            transaction.replace(R.id.fragment_container,frnd_fragment);
                                            transaction.addToBackStack(null);
                                            transaction.commit();


                                        }
                                    });

                                }
                            });
                        }
                    });

//                    reference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                            if(task.isSuccessful())
//                            {
//
//                                // jhamela thik kor ekhaner; path ber kore de
//
//                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                    @Override
//                                    public void onSuccess(Uri urii) {
//                                        files.setPath(urii.toString());
//                                        String key=FirebaseDatabase.getInstance().getReference().child("Upload").push().getKey();
//                                        Map<String, Object> postValues = files.toMap();
//
//                                        Map<String, Object> childUpdates = new HashMap<>();
//                                        childUpdates.put("/Upload/" + key, postValues);
//                                        childUpdates.put("/Upload_unv/"+files.getUniversity()+"/"+key,postValues);
//                                        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void unused) {
//                                                //dismiss progressbar
//                                                progressBar.dismiss();
//                                                Toast.makeText(getActivity(), "File uploaded successfully.", Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//
//
//                                    }
//                                });
//                            }
//                            else
//                            {
//                                Toast.makeText(getActivity(), "Error Occurred", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                }
                else{
                    Toast.makeText(getContext(), "Please fill up all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return binding.getRoot();
    }

    protected void registorNetworkBroadcast(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
           getActivity().registerReceiver(broadcastReceiver , new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }
    protected void unregistorNetwork(){
        try {
            getActivity().unregisterReceiver(broadcastReceiver);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }
    public void onDestroy() {

        super.onDestroy();
        unregistorNetwork();
    }
}
