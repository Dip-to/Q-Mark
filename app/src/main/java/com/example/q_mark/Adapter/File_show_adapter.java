package com.example.q_mark.Adapter;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.Model.Files;
import com.example.q_mark.Model.User;
import com.example.q_mark.R;
import com.example.q_mark.databinding.FileShowSampleBinding;
import com.example.q_mark.utilities.SendToUserProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class File_show_adapter extends RecyclerView.Adapter<File_show_adapter.viewHolder> implements SendToUserProfile {

    Context context;
    ArrayList<Files> list;
    Boolean op=false;

    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    StorageReference ref;

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
        Animation bottom = AnimationUtils.loadAnimation(context, R.anim.top);

        Files model=list.get(position);
        holder.binding.updown.setImageResource(R.drawable.ic_baseline_expand_more_24);

        holder.binding.nameFile.setText(Html.fromHtml("<b>"+"Name: " +"</b >" +"     "+model.getName()));
        holder.binding.course.setText(Html.fromHtml("<b>"+"Cousrse: " +"</b >" +"     "+model.getCourse()));
        holder.binding.subjectt.setText(Html.fromHtml("<b>"+"Subject: " +"</b >" +"     "+model.getSubject()));
        holder.binding.year.setText(Html.fromHtml("<b>"+"Year: " +"</b >" +"     "+model.getYear()));
        holder.binding.unv.setText(Html.fromHtml("<b>"+"University: " +"</b >" +"     "+model.getUniversity()));
        holder.binding.type.setText(Html.fromHtml("<b>"+"Type: " +"</b >" +"     "+model.getType()));
        String s=model.getType();
        Picasso.get().load(model.getPath()).placeholder(R.drawable.ic_pdf).into(holder.binding.img);

        holder.binding.pImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToProfile(view , model.getUploaderid());
            }
        });
        holder.binding.pname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToProfile(view,model.getUploaderid());
            }
        });

        FirebaseDatabase.getInstance().getReference().child("User").child(model.getUploaderid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                holder.binding.pname.setText(user.getName());
                holder.binding.pUniv.setText(user.getUniversity());
                Picasso.get().load(user.getPimage()).placeholder(R.drawable.ic_profile).into(holder.binding.pImage);
            }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        holder.binding.updown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.binding.cl2.getVisibility()==view.GONE)
                {
                    TransitionManager.beginDelayedTransition(holder.binding.cv,new AutoTransition());
                    holder.binding.cl2.setVisibility(view.VISIBLE);
                    holder.binding.updown.setImageResource(R.drawable.ic_baseline_expand_less_24);

                }
                else
                {
                    holder.binding.updown.setImageResource(R.drawable.ic_baseline_expand_more_24);
                    //TransitionManager.beginDelayedTransition(holder.binding.cv,new AutoTransition());
                    holder.binding.cl2.setVisibility(view.GONE);

                }
            }
        });

        holder.binding.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog progressBar = new ProgressDialog(view.getContext());
                progressBar.setCancelable(true);
                progressBar.setMessage("File uploading ...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();

                download(model);

                progressBar.dismiss();
            }
        });
    }

    public void download(Files model)
    {
        String ext = model.getFiletype().toLowerCase().substring(model.getFiletype().toLowerCase().lastIndexOf('/')+1);
        downloadFiles(context,model.getName(),"."+ext,DIRECTORY_DOWNLOADS,model.getPath());
    }
    public void downloadFiles(Context context,String fileName, String fileExtension, String destinationDirectory, String url)
    {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destinationDirectory,fileName+fileExtension);

        downloadManager.enqueue(request);
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
