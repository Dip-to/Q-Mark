package com.example.q_mark.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.q_mark.Fragments.Profile_user;
import com.example.q_mark.Fragments.me_following;
import com.example.q_mark.Model.Notification;
import com.example.q_mark.Model.Post;
import com.example.q_mark.Model.User;
import com.example.q_mark.R;
import com.example.q_mark.commentActivity;
import com.example.q_mark.databinding.PostSampleBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class post_adapter extends RecyclerView.Adapter<post_adapter.viewholder> {

    ArrayList<Post> list;
    Context context;

    public post_adapter(ArrayList<Post> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.post_sample, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Post model = list.get(position);

        //post image
        Glide.with(context).load(model.getPostImage()).fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.ic_profile).into(holder.binding.story);
        //Picasso.get().load(model.getPostImage()).placeholder(R.drawable.ic_profile).into(holder.binding.story);
        holder.binding.like.setText(model.getPostLike() + "");
        holder.binding.comment.setText(model.getCommentCount()+"");
        String pst = model.getPostDescription();
        if (pst.equals("")) {
            holder.binding.postdesc.setVisibility(View.GONE);
        } else {
            holder.binding.postdesc.setText(model.getPostDescription());
            holder.binding.postdesc.setVisibility(View.VISIBLE);
        }
        FirebaseDatabase.getInstance().getReference().child("User").child(model.getPostedBy()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                Picasso.get().load(user.getPimage()).placeholder(R.drawable.ic_profile).into(holder.binding.proImg);
                holder.binding.usName.setText(user.getName());
                holder.binding.puniv.setText("Udvaah");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("posts").child(model.getPostID()).child("likes")
                .child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            holder.binding.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heartsvgrepo, 0, 0, 0);
                            holder.binding.like.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    FirebaseDatabase.getInstance().getReference().child("posts").child(model.getPostID()).child("likes").child(FirebaseAuth.getInstance().getUid())
                                            .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    FirebaseDatabase.getInstance().getReference().child("posts").child(model.getPostID())
                                                            .child("postLike").setValue(model.getPostLike() - 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    holder.binding.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart_24, 0, 0, 0);

                                                                    FirebaseDatabase.getInstance().getReference().child("notification").child(model.getPostedBy())
                                                                            .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void unused) {

                                                                                }
                                                                            });
                                                                }
                                                            });
                                                }
                                            });
                                }
                            });

                        } else {
                            holder.binding.like.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    FirebaseDatabase.getInstance().getReference().child("posts").child(model.getPostID()).child("likes").child(FirebaseAuth.getInstance().getUid())
                                            .setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    FirebaseDatabase.getInstance().getReference().child("posts").child(model.getPostID())
                                                            .child("postLike").setValue(model.getPostLike() + 1)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    holder.binding.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heartsvgrepo, 0, 0, 0);

                                                                    //notification
                                                                    Notification notification=new Notification();
                                                                    notification.setNotificationBy(FirebaseAuth.getInstance().getUid());
                                                                    notification.setNotificationAt(new Date().getTime());
                                                                    notification.setPostId(model.getPostID());
                                                                    notification.setPostedBy(model.getPostedBy());
                                                                    notification.setType("like");

                                                                    FirebaseDatabase.getInstance().getReference().child("notification").child(model.getPostedBy())
                                                                            .push().setValue(notification).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void unused) {

                                                                                }
                                                                            });
                                                                }
                                                            });
                                                }
                                            });
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        holder.binding.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, commentActivity.class);
                intent.putExtra("postid",model.getPostID());
                intent.putExtra("postedby",model.getPostedBy());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.goup,R.anim.godown);

            }
        });
        holder.binding.proImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment ff=new Profile_user();
                //FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("id", model.getPostedBy());
                ff.setArguments(bundle);
                FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,ff);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {


        PostSampleBinding binding;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding = PostSampleBinding.bind(itemView);
        }
    }
}
