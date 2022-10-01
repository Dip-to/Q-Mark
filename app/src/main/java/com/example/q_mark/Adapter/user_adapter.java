package com.example.q_mark.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.R;
import com.example.q_mark.User;
import com.example.q_mark.databinding.UserSampleBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
        Picasso.with(context).load(user.getPimage()).placeholder(R.drawable.ic_friends).into(holder.binding.proImg);
        holder.binding.usName.setText(user.getName());
        holder.binding.textView9.setText("univerity of udvash");
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
