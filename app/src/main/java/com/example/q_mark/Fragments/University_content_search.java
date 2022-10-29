package com.example.q_mark.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.Adapter.File_show_adapter;
import com.example.q_mark.Model.Files;
import com.example.q_mark.databinding.SearchFragmentBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class University_content_search extends Fragment {
    SearchFragmentBinding binding;
    ArrayList<Files> list=new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=SearchFragmentBinding.inflate(inflater,container,false);
        String type=getArguments().getString("type");
        String university=getArguments().getString("university");


        File_show_adapter ff= new File_show_adapter(getContext(),list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.RV.setLayoutManager(linearLayoutManager);
        binding.RV.setAdapter(ff);

        FirebaseDatabase.getInstance().getReference("Upload_unv").child(university).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Files files=dataSnapshot.getValue(Files.class);
                    System.out.println(files.getType()+" "+type);
                    if(files.getType().equals(type))
                    {
                        list.add(files);
                    }
                }
                ff.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();
    }
}
