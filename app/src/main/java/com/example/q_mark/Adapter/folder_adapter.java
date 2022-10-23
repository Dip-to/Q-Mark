package com.example.q_mark.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.Fragments.Upload;
import com.example.q_mark.Model.Notification;
import com.example.q_mark.R;
import com.example.q_mark.databinding.FolderSampleBinding;
import com.example.q_mark.databinding.NotificationSampleBinding;
//import com.example.q_mark.Model;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class folder_adapter extends RecyclerView.Adapter<folder_adapter.viewHolder> {

    Context context;
    ArrayList<Folder> list; //change type

    public folder_adapter(Context context, ArrayList<Folder> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.folder_sample,parent,false);

        return new folder_adapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        folder model=list.get(position); //data type change

        if(model.getIsdirectory())
        {
            holder.binding.folderImage.setImageResource(R.drawable.folder_icon);
        }
        else if(model.getPdf())
        {
            holder.binding.folderImage.setImageResource(R.drawable.pdf_icon);
        }
        else if(model.getImage())
        {
            holder.binding.folderImage.setImageResource(R.drawable.image_icon);
        }

        holder.binding.folderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.getIsdirectory())
                {
                    Intent intent = new Intent(context, Upload.class);
                    String path = model.getFolderPath()+"/"+model.getFolderName();
                    intent.putExtra("path",path);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(model.getImage())
                {
                    Intent intent = new Intent();
                    String type = "image/*";
                    intent.setDataAndType(FirebaseDatabase.getInstance().getReferenceFromUrl(model.getFolderPath()).get(),type);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(model.getPdf())
                {
                    Intent intent = new Intent();
                    String type = "pdf/*";
                    intent.setDataAndType(FirebaseDatabase.getInstance().getReferenceFromUrl(model.getFolderPath()).get(),type);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        FolderSampleBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = FolderSampleBinding.bind(itemView);
        }
    }
}
