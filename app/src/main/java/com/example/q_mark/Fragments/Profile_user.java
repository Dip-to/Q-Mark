package com.example.q_mark.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.q_mark.Model.User;
import com.example.q_mark.R;
import com.example.q_mark.databinding.UserProfileBinding;
import com.example.q_mark.databinding.UserSampleBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profile_user extends Fragment {
    UserProfileBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=UserProfileBinding.inflate(inflater,container,false);
        String s=getArguments().getString("id");
        FirebaseDatabase.getInstance().getReference().child("User").child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                binding.nameProfile.setText(user.getName());
                binding.uniName.setText("udvash");
                binding.email1.setText(user.getEmail());
                binding.emailTex.setText(user.getEmail());
                binding.phn.setText(user.getMobile());
                binding.fllwrCnt.setText(String.valueOf(user.getFollowerCount()));
                binding.followCnt.setText(String.valueOf(user.getFollowingCount()));
                Picasso.get().load(user.getPimage()).placeholder(R.drawable.ic_profile).into(binding.proImg);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return binding.getRoot();
    }
}
