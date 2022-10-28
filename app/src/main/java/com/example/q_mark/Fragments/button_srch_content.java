package com.example.q_mark.Fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.Adapter.File_show_adapter;
import com.example.q_mark.Adapter.notification_adapter;
import com.example.q_mark.Model.Files;
import com.example.q_mark.Model.Notification;
import com.example.q_mark.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class button_srch_content extends Fragment {

    ArrayList<Files> list=new ArrayList<>();
    RecyclerView rv;
    EditText srch;
    File_show_adapter ff;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv=getView().findViewById(R.id.RV);
        ff= new File_show_adapter(getContext(),list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(ff);
        srch=getView().findViewById(R.id.searchbar);
        FirebaseDatabase.getInstance().getReference().child("Upload").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Files files=dataSnapshot.getValue(Files.class);
                    list.add(files);
                }
                ff.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        srch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                srch(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




    }

    private void srch(String s) {

        //serach
        Query query=FirebaseDatabase.getInstance().getReference("Upload").orderByChild("hint");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Files files = dataSnapshot.getValue(Files.class);
                    if(files.getHint().toLowerCase().contains(s.toLowerCase()))
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
