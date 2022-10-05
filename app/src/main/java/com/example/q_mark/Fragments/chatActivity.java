package com.example.q_mark.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.q_mark.Adapter.ChatAdapter;
import com.example.q_mark.Model.ChatMessage;
import com.example.q_mark.Model.User;
import com.example.q_mark.R;
import com.example.q_mark.databinding.ActivityChatScreenBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class chatActivity extends Fragment {

    private ActivityChatScreenBinding binding;
    private User receiverUser;
    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityChatScreenBinding.inflate(inflater,container,false);
        System.out.println("ami humiliate  kori na");
        //View view = LayoutInflater.from(context).inflate(R.layout.user_sample,parent,false);
        setListener();
        loadReceiverDetails();
        return binding.getRoot();
    }
private void loadReceiverDetails(){

        String s=getArguments().getString("rcv");
        System.out.println("hi ami asciii "+s);
        FirebaseDatabase.getInstance().getReference().child("User").child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                receiverUser=snapshot.getValue(User.class);
                receiverUser.setUid(s);
                System.out.println("from database "+receiverUser.getName());
                binding.friendsName.setText(receiverUser.getName());

                Picasso.with(getContext()).load(receiverUser.getPimage()).placeholder(R.drawable.ic_profile).into(binding.proImg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

}

private void setListener(){
        binding.chatImageBack.setOnClickListener(view -> getActivity().onBackPressed());
}

}
