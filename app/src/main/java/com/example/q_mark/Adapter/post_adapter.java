package com.example.q_mark.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.Model.Post;
import com.example.q_mark.Model.User;
import com.example.q_mark.R;
import com.example.q_mark.databinding.PostSampleBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class post_adapter extends RecyclerView.Adapter<post_adapter.viewholder>{

    ArrayList<Post> list;
    Context context;

    public post_adapter(ArrayList<Post> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.post_sample,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Post model=list.get(position);
        Picasso.with(context).load(model.getPostImage()).placeholder(R.drawable.ic_profile).into(holder.binding.story);
        FirebaseDatabase.getInstance().getReference().child("User").child(model.getPostedBy()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);

                Picasso.with(context).load(user.getPimage()).placeholder(R.drawable.ic_profile).into(holder.binding.proImg);
                holder.binding.usName.setText(user.getName());
                holder.binding.puniv.setText("Udvaah");




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        PostSampleBinding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding=PostSampleBinding.bind(itemView);
        }
    }
}
