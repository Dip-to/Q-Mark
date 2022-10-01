package com.example.q_mark.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.Model.Follower;
import com.example.q_mark.R;
import com.example.q_mark.Model.User;
import com.example.q_mark.databinding.UserSampleBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class user_adapter extends RecyclerView.Adapter<user_adapter.viewholder> {
    Context context;

    public user_adapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    ArrayList<User> list;

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_sample,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        User user=list.get(position);
        Picasso.with(context).load(user.getPimage()).placeholder(R.drawable.ic_profile).into(holder.binding.proImg);
        holder.binding.usName.setText(user.getName());
        holder.binding.puniv.setText("univerity of udvash");

        holder.binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Follower follower=new Follower();
                follower.setFollowedby(FirebaseAuth.getInstance().getUid());
                follower.setFollwedate(new Date().getTime());

                FirebaseDatabase.getInstance().getReference().child("User")
                        .child(user.getUid())
                        .child("followers")
                        .child(FirebaseAuth.getInstance().getUid())
                        .setValue(follower).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                FirebaseDatabase.getInstance().getReference().child("User")
                                        .child(user.getUid())
                                        .child("followerC ount")
                                        .setValue(user.getFollowerCount()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(context, "You followed" +user.getName(), Toast.LENGTH_SHORT).show();
                                            }
                                        });


                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        UserSampleBinding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding=UserSampleBinding.bind(itemView);

        }
    }
}
