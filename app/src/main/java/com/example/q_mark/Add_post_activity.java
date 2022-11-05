package com.example.q_mark;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q_mark.Model.Post;
import com.example.q_mark.Model.User;
import com.example.q_mark.databinding.FragmentAddPostBinding;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class Add_post_activity extends AppCompatActivity {

    ActivityResultLauncher<String> launcher;
    Uri img;
    FirebaseAuth mauth;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    ProgressDialog dialog;
    ImageView imgselect,proImg,imgvw;
    Button addpostBtn;
    TextView usName,puniv;
    EditText posttext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.fragment_add_post);
        mauth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseStorage=firebaseStorage.getInstance();
        dialog=new ProgressDialog(this);
        imgselect=findViewById(R.id.imgselect);
        addpostBtn=findViewById(R.id.addpost_btn);
        puniv=findViewById(R.id.puniv);
        usName=findViewById(R.id.usName);
        proImg=findViewById(R.id.proImg);
        posttext=findViewById(R.id.posttext);
        imgvw=findViewById(R.id.imgvw);



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
                    Picasso.get().load(user.getPimage()).placeholder(R.drawable.ic_profile).into(proImg);
                    usName.setText(user.getName());
                    puniv.setText(user.getUniversity());
                    Picasso.get().load(user.getPimage()).into(proImg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       posttext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String description=posttext.getText().toString();
                if(!description.isEmpty() || img!=null)
                {
                   addpostBtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplication(),R.drawable.follow_btn));
                   addpostBtn.setTextColor(getApplication().getResources().getColor(R.color.white));
                   addpostBtn.setEnabled(true);
                }
                else
                {
                    addpostBtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplication(),R.drawable.followdbutton));
                    addpostBtn.setTextColor(getApplication().getResources().getColor(R.color.gray));
                    addpostBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

       imgselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("image/*");

                if(img!=null)
                {



                }

            }
        });
        launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result!=null)
                {
                    Intent intent=new Intent(Add_post_activity.this, Cropclass.class);
                    intent.putExtra("Data",result.toString());
                    startActivityForResult(intent,100);




                }
            }
        });

       ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
               new ActivityResultContracts.StartActivityForResult(),
               new ActivityResultCallback<ActivityResult>() {
                   @Override
                   public void onActivityResult(ActivityResult result) {
                       System.out.println(result.getResultCode()+"res code");
//
                       Intent data=result.getData();
                       Uri u=Uri.parse(data.getStringExtra("result"));
                       imgvw.setImageURI(u);
                       img=u;
                       System.out.println("image here : "+img.toString());
                       imgvw.setVisibility(View.VISIBLE);
//
                   }
               });
       addpostBtn.setOnClickListener(new View.OnClickListener() {

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
                                post.setPostDescription(posttext.getText().toString());
                                post.setPostedBy(FirebaseAuth.getInstance().getUid());
                                post.setPostedAt(new Date().getTime());


                                firebaseDatabase.getReference().child("posts").push().setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialog.dismiss();
                                        Toast.makeText(Add_post_activity.this, "Posted Successfully", Toast.LENGTH_SHORT).show();
//                                        Fragment ff=new Home();
//                                        //FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
//                                        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
//                                        transaction.replace(R.id.fragment_container,ff);
//                                        transaction.addToBackStack(null);
//                                        transaction.commit();
                                        finish();
                                    }
                                });
                            }
                        });
                    }
                });

            }
        });


    }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
       System.out.println(resultCode+" "+requestCode);
           if(resultCode==101 && requestCode==100)
           {
               System.out.println(requestCode+"res code");
               System.out.println(data.getStringExtra("hello"));
               String result=data.getStringExtra("result");
               System.out.println(result);
               Uri uri=data.getData();
               if(result!=null)
               {
                   uri=Uri.parse(result);
                   imgvw.setImageURI(uri);
                   img=uri;
               }
           }


//           String ss= (String) data.getExtras().get("result");
//           System.out.println(data.getStringExtra("hello"));
//           Uri u=Uri.parse(ss);
//           imgvw.setImageURI(u);
//           img=u;
//           System.out.println("image here : "+img.toString());
//           imgvw.setVisibility(View.VISIBLE);

   }
}