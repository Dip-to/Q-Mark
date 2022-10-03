package com.example.q_mark.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.q_mark.Model.User;
import com.example.q_mark.R;
import com.example.q_mark.databinding.FragmentAddPostBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class add_post extends Fragment {

    public add_post() {
    }

    FragmentAddPostBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentAddPostBinding.inflate(inflater,container,false);


        FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    User user=snapshot.getValue(User.class);
                    Picasso.with(getContext()).load(user.getPimage()).placeholder(R.drawable.ic_profile).into(binding.proImg);
                    binding.usName.setText(user.getName());
                    binding.puniv.setText("univerity of udvash");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.posttext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String description=binding.posttext.getText().toString();
                if(!description.isEmpty())
                {
                    binding.addpostBtn.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.follow_btn));
                    binding.addpostBtn.setTextColor(getContext().getResources().getColor(R.color.white));
                    binding.addpostBtn.setEnabled(true);
                }
                else
                {
                    binding.addpostBtn.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.followdbutton));
                    binding.addpostBtn.setTextColor(getContext().getResources().getColor(R.color.gray));
                    binding.addpostBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return binding.getRoot();

    }
}