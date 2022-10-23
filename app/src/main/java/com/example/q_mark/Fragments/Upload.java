package com.example.q_mark.Fragments;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.Adapter.folder_adapter;
import com.example.q_mark.MainActivity;
import com.example.q_mark.Model.Folder;
import com.example.q_mark.Model.Notification;

import com.example.q_mark.R;
import com.example.q_mark.databinding.UploadBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;
import android.app.ProgressDialog;


public class Upload extends Fragment {

    RecyclerView recyclerView;
    TextView noFilesText;

    private ArrayList<Folder>list = new ArrayList<>();
    private String curPath;
    UploadBinding binding;


    //firebase objects
    FirebaseAuth mauth;
    DatabaseReference firebaseDatabase;
    FirebaseStorage firebaseStorage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        curPath = Intent.getStringExtra("path");
        if(curPath.length()==0 || curPath == null)
        {
            curPath = "https://q-mark-dd305-default-rtdb.firebaseio.com/Upload/" + FirebaseAuth.getInstance().getUid();
        }

        //Upload -> folder1
        //cur -> cur + "/folder1";
        mauth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance().getReferenceFromUrl(curPath);
        firebaseStorage= FirebaseStorage.getInstance();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        folder_adapter fap= new folder_adapter(getContext(),list);
        binding = UploadBinding.inflate(inflater,container,false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(fap);
        binding.addPdfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        FirebaseDatabase.getInstance().getReferenceFromUrl(curPath).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Folder newFolder = dataSnapshot.getValue(Folder.class);
                    list.add(newFolder);
                }
                fap.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }

}
