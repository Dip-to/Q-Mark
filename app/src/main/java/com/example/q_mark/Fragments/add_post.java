package com.example.q_mark.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.q_mark.Model.Post;
import com.example.q_mark.Model.User;
import com.example.q_mark.R;
import com.example.q_mark.databinding.FragmentAddPostBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Date;


public class add_post extends Fragment {

    public add_post() {
    }
    ActivityResultLauncher<String> launcher;
    FragmentAddPostBinding binding;
    Uri img;

    FirebaseAuth mauth;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    ProgressDialog dialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mauth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseStorage=firebaseStorage.getInstance();
        dialog=new ProgressDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentAddPostBinding.inflate(inflater,container,false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Post Uploading");
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    User user=snapshot.getValue(User.class);
                    Picasso.get().load(user.getPimage()).placeholder(R.drawable.ic_profile).into(binding.proImg);
                    binding.usName.setText(user.getName());
                    binding.puniv.setText("univerity of udvash");
                    Picasso.get().load(user.getPimage()).into(binding.proImg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.posttext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String description=binding.posttext.getText().toString();
                if(!description.isEmpty())
                {
                    binding.addpostBtn.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.follow_btn));
                    binding.addpostBtn.setTextColor(getContext().getResources().getColor(R.color.white));
                    binding.addpostBtn.setEnabled(true);
                }
                else
                {
                    binding.addpostBtn.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.followdbutton));
                    binding.addpostBtn.setTextColor(getContext().getResources().getColor(R.color.gray));
                    binding.addpostBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.imgselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("image/*");

                if(img!=null)
                {
                    // do shit


                }

            }
        });
        launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result!=null)
                {
                    binding.imgvw.setImageURI(result);
                    img=result;
                    binding.imgvw.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.addpostBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                dialog.show();
                final StorageReference storageReference=firebaseStorage.getReference().child("posts").child(FirebaseAuth.getInstance().getUid())
                        .child(new Date().getTime()+" ");
                storageReference.putFile(img).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Post post=new Post();
                                post.setPostImage(uri.toString());
                                post.setPostDescription(binding.posttext.getText().toString());
                                post.setPostedBy(FirebaseAuth.getInstance().getUid());
                                post.setPostedAt(new Date().getTime());


                                firebaseDatabase.getReference().child("posts").push().setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialog.dismiss();
                                        Toast.makeText(getContext(), "Posted Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });

            }
        });

        return binding.getRoot();

    }
}