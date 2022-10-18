package com.example.q_mark.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.Model.Notification;
import com.example.q_mark.R;
import com.example.q_mark.databinding.NotificationSampleBinding;

import java.util.ArrayList;

public class folder_adapter extends RecyclerView.Adapter<folder_adapter.viewHolder> {

    Context context;
    ArrayList<Notification> list; //change type

    public folder_adapter(Context context, ArrayList<Notification> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
//        View view= LayoutInflater.from(context).inflate(R.layout.comment_sample,parent,false);
//        return new folder_adapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Notification model=list.get(position); //data type change


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{


        public viewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
