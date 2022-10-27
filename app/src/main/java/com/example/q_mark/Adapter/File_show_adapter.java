package com.example.q_mark.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.Model.Files;
import com.example.q_mark.R;
import com.example.q_mark.databinding.FileShowSampleBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class File_show_adapter extends RecyclerView.Adapter<File_show_adapter.viewHolder> {

    Context context;
    ArrayList<Files> list;

    public File_show_adapter(Context context, ArrayList<Files> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.file_show_sample,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Files model=list.get(position);

        holder.binding.nameFile.setText(Html.fromHtml("<b>"+"Name: " +"</b >" +"     "+model.getName()));
        holder.binding.course.setText(Html.fromHtml("<b>"+"Cousrse: " +"</b >" +"     "+model.getCourse()));
        holder.binding.subjectt.setText(Html.fromHtml("<b>"+"Subject: " +"</b >" +"     "+model.getSubject()));
        holder.binding.year.setText(Html.fromHtml("<b>"+"Year: " +"</b >" +"     "+model.getYear()));
        holder.binding.unv.setText(Html.fromHtml("<b>"+"University Name: " +"</b >" +"     "+model.getUniversity()));

        String s=model.getType();
        Picasso.get().load(model.getPath()).placeholder(R.drawable.ic_pdf).into(holder.binding.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        FileShowSampleBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=FileShowSampleBinding.bind(itemView);
        }
    }
}
