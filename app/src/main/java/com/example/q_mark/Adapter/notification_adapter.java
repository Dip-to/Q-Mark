package com.example.q_mark.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.Model.Notification;
import com.example.q_mark.R;
import com.example.q_mark.databinding.CommentSampleBinding;
import com.example.q_mark.databinding.NotificationBinding;

import java.util.ArrayList;

public class notification_adapter extends RecyclerView.Adapter<notification_adapter.viewHolder> {

    Context context;
    ArrayList<Notification>list;

    public notification_adapter(Context context, ArrayList<Notification> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.notification,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Notification model=list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        NotificationBinding binding;
        public viewHolder(@NonNull View itemView) {

            super(itemView);
            binding = NotificationBinding.bind(itemView);

        }
    }
}
