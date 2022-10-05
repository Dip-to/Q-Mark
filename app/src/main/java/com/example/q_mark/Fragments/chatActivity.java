package com.example.q_mark.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
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
import com.example.q_mark.utilities.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class chatActivity extends Fragment {

    private ActivityChatScreenBinding binding;
    private User receiverUser;
    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
    private PreferenceManager preferenceManager;
    FirebaseFirestore database;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityChatScreenBinding.inflate(inflater,container,false);
        //View view = LayoutInflater.from(context).inflate(R.layout.user_sample,parent,false);
        setListener();
        loadReceiverDetails();
        //init();
        return binding.getRoot();
    }
//    public void init()
//    {
//        //preferenceManager= (PreferenceManager) PreferenceManager.getDefaultSharedPreferences(getActivity());
//        chatMessages=new ArrayList<>();
//        System.out.println(receiverUser.getPimage());
//        chatAdapter=new ChatAdapter(chatMessages,receiverUser.getPimage(), FirebaseAuth.getInstance().getUid());
//        binding.chatScreenRv.setAdapter(chatAdapter);
//        database=FirebaseFirestore.getInstance();
//    }
    private void sendMessage() {
        Map<String, Object> message = new HashMap<>();
        message.put(Constants.KEY_SENDER_ID, FirebaseAuth.getInstance().getUid());
        message.put(Constants.KEY_RECEIVER_ID, receiverUser.getUid());
        message.put(Constants.KEY_MESSAGE ,binding.inputMessage.getText().toString());
        message.put(Constants.KEY_TIMESTAMP, new Date().toString());
        database.collection(Constants.KEY_COLLECTION_CHAT).add(message).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                System.out.println("hoise");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("hoy ni");
            }
        });
        binding.inputMessage.setText(null);
    }
private void loadReceiverDetails(){

        String s=getArguments().getString("rcv");
        FirebaseDatabase.getInstance().getReference().child("User").child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                receiverUser=snapshot.getValue(User.class);
                receiverUser.setUid(s);
                System.out.println("from database "+receiverUser.getName());
               // System.out.println(receiverUser.getPimage());
                binding.friendsName.setText(receiverUser.getName());
                Picasso.with(getContext()).load(receiverUser.getPimage()).placeholder(R.drawable.ic_profile).into(binding.proImg);

                chatMessages=new ArrayList<>();
                System.out.println(receiverUser.getPimage());
                chatAdapter=new ChatAdapter(chatMessages,receiverUser.getPimage(), FirebaseAuth.getInstance().getUid());
                binding.chatScreenRv.setAdapter(chatAdapter);
                database=FirebaseFirestore.getInstance();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

}

private void setListener(){
        binding.chatImageBack.setOnClickListener(view -> getActivity().onBackPressed());
        binding.layoutSend.setOnClickListener(view -> sendMessage());
}

}
