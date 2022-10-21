package com.example.q_mark;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.q_mark.Adapter.user_adapter;
import com.example.q_mark.Model.User;
import com.example.q_mark.databinding.SearchBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class Search extends Fragment {

    private static final String TAG ="UserSearchActivity" ;
    private static final int NUM_GRID_COLUMNS = 3;

    ArrayList<User> list=new ArrayList<>();
    FirebaseAuth mauth;
    FirebaseDatabase firebaseDatabase;
    SearchBinding binding;
    user_adapter us;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding=SearchBinding.inflate(inflater,container,false);
        us=new user_adapter(getContext(),list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.RV.setLayoutManager(linearLayoutManager);
        binding.RV.setAdapter(us);

        firebaseDatabase.getReference().child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(binding.searchbar.getText().toString().equals(""))
                {
                    list.clear();
                    for(DataSnapshot dataSnapshot: snapshot.getChildren())
                    {

                        User user=dataSnapshot.getValue(User.class);
                        user.setUid(dataSnapshot.getKey());
                        System.out.println(dataSnapshot.getKey());
                        if(!dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getUid()))
                        {
                            list.add(user);
                        }
                    }
                    us.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //search
        binding.searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUser(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        return binding.getRoot();


    }

    private void searchUser(String s) {
        Query query=FirebaseDatabase.getInstance().getReference("User").orderByChild("Name");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    User user=dataSnapshot.getValue(User.class);
                    user.setUid(dataSnapshot.getKey());
                    System.out.println(dataSnapshot.getKey());
                    if(!dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getUid()) && user.getName().toLowerCase().contains(s))
                    {
                        list.add(user);
                    }
                }
                us.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mauth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
    }
}
