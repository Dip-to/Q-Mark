package com.example.q_mark.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    String type,university;
    File_show_adapter ff;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=SearchFragmentBinding.inflate(inflater,container,false);
        type=getArguments().getString("type");
        university=getArguments().getString("university");


        ff= new File_show_adapter(getContext(),list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.RV.setLayoutManager(linearLayoutManager);
        binding.RV.setAdapter(ff);
        binding.searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println(charSequence.toString().toLowerCase());
                srch(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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

    private void srch(String toLowerCase) {
        FirebaseDatabase.getInstance().getReference("Upload_unv").child(university).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Files files=dataSnapshot.getValue(Files.class);
                    System.out.println(files.getType()+" "+type);
                    if(files.getType().equals(type) && files.getHint().toLowerCase().contains(toLowerCase.toLowerCase()))
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
    }
}
