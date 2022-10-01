package com.example.q_mark.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.Model.Follower;
import com.example.q_mark.Model.User;
import com.example.q_mark.R;
import com.example.q_mark.databinding.MyFriendSampleBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class follower_adapter extends RecyclerView.Adapter<follower_adapter.viewHolder> {
    Context context;
    ArrayList<User> list;

    public follower_adapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_friend_sample,parent,false);
        return new viewHolder(view);




    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        User model=list.get(position);

        System.out.println(model.getName()+"haha");
        Picasso.with(context).load(model.getPimage()).placeholder(R.drawable.ic_profile).into(holder.binding.pImage);
        holder.binding.pname.setText(model.getName());
        holder.binding.pUniv.setText("univerity of udvash");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        MyFriendSampleBinding binding;
        //ImageView img;
        public viewHolder(@NonNull View itemView) {

            super(itemView);
            binding=MyFriendSampleBinding.bind(itemView);
            //img=itemView.findViewById(R.id.pImage);

        }
    }

}
