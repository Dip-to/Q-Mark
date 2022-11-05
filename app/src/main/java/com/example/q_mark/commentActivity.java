package com.example.q_mark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.q_mark.Adapter.Comment_adapter;
import com.example.q_mark.Model.Comment;
import com.example.q_mark.Model.Notification;
import com.example.q_mark.Model.Post;
import com.example.q_mark.Model.User;
import com.example.q_mark.databinding.ActivityCommentBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class commentActivity extends AppCompatActivity {

    ActivityCommentBinding binding;
    Intent intent;
    String postid,postedby;
    FirebaseAuth mauth;
    FirebaseDatabase database;
    ArrayList<Comment> list=new ArrayList<>();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.goup,R.anim.godown);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        getSupportActionBar().hide();

        binding=ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent=getIntent();
        postid=intent.getStringExtra("postid");
        postedby=intent.getStringExtra("postedby");
        mauth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        database.getReference().child("posts").child(postid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post post=snapshot.getValue(Post.class);
                Glide.with(getApplicationContext()).load(post.getPostImage()).fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.ic_profile).into(binding.postimage);
                String pp=post.getPostDescription();
                if (pp.equals("")) {
                   binding.posttextdesc.setVisibility(View.GONE);
                } else {
                    binding.posttextdesc.setText(post.getPostDescription());
                    binding.posttextdesc.setVisibility(View.VISIBLE);
                }
                binding.like.setText(post.getPostLike()+"");
                binding.comment.setText(post.getCommentCount()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("User").child(postedby).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                Picasso.get().load(user.getPimage()).placeholder(R.drawable.ic_profile).into(binding.pImage);
                binding.usName.setText(user.getName());
                binding.puniv.setText(user.getUniversity());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //comment add
        binding.addcmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment comment=new Comment();
                comment.setCommentText(binding.addcmnttext.getText().toString());
                comment.setCommentedBy(FirebaseAuth.getInstance().getUid());
                comment.setCommentedAt(new Date().getTime());

                database.getReference().child("posts").child(postid).child("comments").push().setValue(comment)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                database.getReference().child("posts").child(postid).child("commentCount").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        int commentCount=0;
                                        if(snapshot.exists())
                                        {
                                            commentCount=snapshot.getValue(Integer.class);
                                        }
                                        database.getReference().child("posts").child(postid).child("commentCount").setValue(commentCount+1)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                binding.addcmnttext.setText("");
                                                Toast.makeText(commentActivity.this, "Commented", Toast.LENGTH_SHORT).show();

                                                Notification notification=new Notification();
                                                notification.setNotificationAt(new Date().getTime());
                                                notification.setNotificationBy(FirebaseAuth.getInstance().getUid());
                                                notification.setPostId(postid);
                                                notification.setPostedBy(postedby);
                                                notification.setType("comment");

                                                FirebaseDatabase.getInstance().getReference().child("notification")
                                                        .child(postedby).push().setValue(notification).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {

                                                            }
                                                        });

                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        });

            }
        });
        Comment_adapter comment_adapter=new Comment_adapter(this,list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        binding.rv.setLayoutManager(linearLayoutManager);
        binding.rv.setAdapter(comment_adapter);

        database.getReference().child("posts").child(postid).child("comments")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for(DataSnapshot dataSnapshot: snapshot.getChildren())
                        {
                            Comment comment=dataSnapshot.getValue(Comment.class);
                            System.out.println(comment.getCommentText());
                            list.add(comment);
                        }
                        comment_adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}