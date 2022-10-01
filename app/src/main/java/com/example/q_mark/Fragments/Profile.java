package com.example.q_mark.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.q_mark.Fragments.my_followers;
import com.example.q_mark.R;
import com.example.q_mark.fbase_userdata;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

public class Profile extends Fragment {
    private TextView pro_name,email1,email2,mobile1;
    private ImageView pro_img;
    private Button brws;
    DatabaseReference databaseReference;
    LinearLayout mfrnds,following;
    Uri img;
    private String s;

    ActivityResultLauncher<String> launcher;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile,container,false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pro_name=getView().findViewById(R.id.name_profile);
        email1=getView().findViewById(R.id.email1);
        email2=getView().findViewById(R.id.email2);
        mobile1=getView().findViewById(R.id.mobile);
        pro_img=getView().findViewById(R.id.proImg);
        brws=getView().findViewById(R.id.browse);
        mfrnds=getView().findViewById(R.id.frnds_btn);
        following=getView().findViewById(R.id.following);

        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frnd_fragment=new me_following();
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,frnd_fragment).commit();
            }
        });


        mfrnds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frnd_fragment=new my_followers();
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,frnd_fragment).commit();


            }
        });



        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://q-mark-dd305-default-rtdb.firebaseio.com/User");
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference st=FirebaseStorage.getInstance().getReference();

        FirebaseUser mUser= FirebaseAuth.getInstance().getCurrentUser();
        s=mUser.getUid();
        fbase_userdata f=new fbase_userdata();
        ArrayList<String> pp= new ArrayList<String>();

        brws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("image/*");

                if(img!=null)
                {

                    uploadimg();
                }
            }
        });

        launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result!=null)
                {
                    pro_img.setImageURI(result);
                    img=result;
                }
            }
        });




        databaseReference.child(s).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    String name = String.valueOf(dataSnapshot.child("Name").getValue());
                    String email = String.valueOf(dataSnapshot.child("Email").getValue());
                    String mpbile = String.valueOf(dataSnapshot.child("Mobile").getValue());
                    String pimg = String.valueOf(dataSnapshot.child("Pimage").getValue());

                    pro_name.setText(name);
                    email1.setText(email);
                    email2.setText(email);
                    mobile1.setText(mpbile);
                    //System.out.println(pimg);
                    if(!pimg.equals("###"))
                    {
                        try {
                            Picasso.with(pro_img.getContext()).load(pimg).into(pro_img);
//                            Glide.with(this)
//                                    .load(pro_img.getContext())
//                                    .transform(CircleCrop())
//                                    .into(pro_img)
                        }
                        catch (Exception e)
                        {
                            System.out.println("hello "+e );
                        }
                    }



                }
            }
        });






    }

    private void uploadimg() {
        StorageReference reference=FirebaseStorage.getInstance().getReference().child("Profile_image/"+ UUID.randomUUID().toString());
        reference.putFile(img).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getActivity(),"Image Uploaded",Toast.LENGTH_SHORT).show();
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            databaseReference.child(s).child("Pimage").setValue(uri.toString());
                        }
                    });
                    //databaseReference.child("User").child(s).child("Pimage").setValue("###");
                }
                else
                {
                    Toast.makeText(getActivity(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
