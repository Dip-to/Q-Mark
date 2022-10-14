package com.example.q_mark.Fragments;

import android.os.Bundle;
//import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.q_mark.Adapter.ChatAdapter;
import com.example.q_mark.Model.ChatMessage;
import com.example.q_mark.Model.User;
import com.example.q_mark.R;
import com.example.q_mark.databinding.ActivityChatScreenBinding;
import com.example.q_mark.utilities.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class chatActivity extends Fragment {

    private ActivityChatScreenBinding binding;
    private User receiverUser;
    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
  //  private PreferenceManager preferenceManager;
    FirebaseFirestore database;
    private String rcvruserid;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityChatScreenBinding.inflate(inflater,container,false);
        //View view = LayoutInflater.from(context).inflate(R.layout.user_sample,parent,false);
        setListener();
        loadReceiverDetails();
        listenMessages();
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
        message.put(Constants.KEY_TIMESTAMP, FieldValue.serverTimestamp());
        database.collection(Constants.KEY_COLLECTION_CHAT).add(message).addOnSuccessListener(documentReference -> System.out.println("hoise")).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("hoy ni");
            }
        });
        binding.inputMessage.setText(null);

    }

    private void  listenMessages(){

        //msgs rcvd by the  received user
       //dipto [only this line]   FirebaseFirestore.getInstance().collection(Constants.KEY_COLLECTION_CHAT)
//                .whereEqualTo(Constants.KEY_SENDER_ID,rcvruserid)
//                .whereEqualTo(Constants.KEY_RECEIVER_ID,FirebaseAuth.getInstance().getUid())
               //dipto [only this line] .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    //dipto suru
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if(error != null){
//                            System.out.println(error.getMessage());
//                            return;
//                        }
//                        if(value!=null){
//                            int count =chatMessages.size();
//                            for (DocumentChange documentChange : value.getDocumentChanges()){
//                                if(documentChange.getType() == DocumentChange.Type.ADDED){
//                                    ChatMessage chatMessage = new ChatMessage();
//                                    chatMessage.senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
//                                    chatMessage.receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
//                                    chatMessage.message = documentChange.getDocument().getString(Constants.KEY_MESSAGE);
//                                  //  chatMessage.dateTime = getReadableDateTime(new Date(String.valueOf(documentChange.getDocument().getTimestamp(Constants.KEY_TIMESTAMP))));
//                                    //chatMessage.dateObject= documentChange.getDocument().getTimestamp(Constants.KEY_TIMESTAMP).toDate();
//                                    chatMessages.add(chatMessage);
//
//                                }
//
//                            }
//                            Log.d("FCM","middle");
                            //dipto sesh

//                            FirebaseFirestore.getInstance().collection(Constants.KEY_COLLECTION_CHAT)
////                .whereEqualTo(Constants.KEY_SENDER_ID,FirebaseAuth.getInstance().getUid())
////                .whereEqualTo(Constants.KEY_RECEIVER_ID,rcvruserid)
//                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                                        @Override
//                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
////                                            L        at com.example.q_mark.Fragments.chatActivity$2.onEvent(chatActivity.java:120)og.d("FCM","middle123");
//
//                                            if(error != null){
//                                                System.out.println(error.getMessage());
//                                                return;
//                                            }
//                                            if(value!=null) {
//                                                int count = chatMessages.size();
//                                                for (DocumentChange documentChange : value.getDocumentChanges()) {
//                                                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
//                                                        ChatMessage chatMessage = new ChatMessage();
//                                                        chatMessage.senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
//                                                        chatMessage.receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
//                                                        chatMessage.message = documentChange.getDocument().getString(Constants.KEY_MESSAGE);
//                                                        chatMessage.dateTime = getReadableDateTime(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP));
//                                                        chatMessage.dateObject= documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
//                                                        chatMessages.add(chatMessage);
//                                                    }
//
//                                                }
//                                                 Collections.sort(chatMessages,(obj1, obj2)-> obj1.dateObject.compareTo(obj2.dateObject));
//                            if(count == 0){
//                                chatAdapter.notifyDataSetChanged();
//                            }else{
//                                chatAdapter.notifyItemRangeInserted(chatMessages.size(),chatMessages.size());
//                                binding.chatScreenRv.setVisibility(chatMessages.size() -1);
//                            }
//                            binding.chatScreenRv.setVisibility(View.VISIBLE);
//                        }
//                        binding.chatProgressBar.setVisibility(View.GONE);
//                    }
//                });
                            //dipto suru
//                          // Collections.sort(chatMessages,(obj1, obj2)-> obj1.dateObject.compareTo(obj2.dateObject));
//                            if(count == 0){
//                                chatAdapter.notifyDataSetChanged();
//                            }else{
//                                chatAdapter.notifyItemRangeInserted(chatMessages.size(),chatMessages.size());
//                                binding.chatScreenRv.setVisibility(chatMessages.size() -1);
//                            }
//                            binding.chatScreenRv.setVisibility(View.VISIBLE);
//                        }
//                        binding.chatProgressBar.setVisibility(View.GONE);
//                    }
//                });
//        System.out.println("in the middle");
        //dipto sesh

//        FirebaseFirestore.getInstance().collection(Constants.KEY_COLLECTION_CHAT)
//               .whereEqualTo(Constants.KEY_SENDER_ID,FirebaseAuth.getInstance().getUid())
//                .whereEqualTo(Constants.KEY_RECEIVER_ID,receiverUser)
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if(error != null){
//                            System.out.println(error.getMessage()+"haha");
//                            return;
//                        }
//                        if(value!=null){
//                            int count =chatMessages.size();
//                            for (DocumentChange documentChange : value.getDocumentChanges()){
//                                if(documentChange.getType() == DocumentChange.Type.ADDED){
//                                    ChatMessage chatMessage = new ChatMessage();
//                                    chatMessage.senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
//                                    chatMessage.receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
//                                    chatMessage.message = documentChange.getDocument().getString(Constants.KEY_MESSAGE);
//                                    System.out.println(chatMessage.message+" hoy na "+chatMessage.senderId);
//                                      chatMessage.dateTime = String.valueOf(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP).toString());
//                                    //                       chatMessage.dateObject= documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
//                                    chatMessages.add(chatMessage);
//                                }
//
//                            }
//
//                            //Collections.sort(chatMessages,(obj1,obj2)-> obj1.dateObject.compareTo(obj2.dateObject));
//                            if(count == 0){
//                                chatAdapter.notifyDataSetChanged();
//                            }else{
//                                chatAdapter.notifyItemRangeInserted(chatMessages.size(),chatMessages.size());
//                                binding.chatScreenRv.setVisibility(chatMessages.size() -1);
//                            }
//                            binding.chatScreenRv.setVisibility(View.VISIBLE);
//                        }
//                        binding.chatProgressBar.setVisibility(View.GONE);
//                    }
//
//                });



//
        FirebaseFirestore.getInstance().collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID,FirebaseAuth.getInstance().getUid())
               .whereEqualTo(Constants.KEY_RECEIVER_ID,rcvruserid)
                .addSnapshotListener(eventListener);

        FirebaseFirestore.getInstance().collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID,rcvruserid)
                .whereEqualTo(Constants.KEY_RECEIVER_ID,FirebaseAuth.getInstance().getUid())
                .addSnapshotListener(eventListener);
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if(error != null){
            return;
        }
        if(value!=null){
            int count =chatMessages.size();
            for (DocumentChange documentChange : value.getDocumentChanges()){
                if(documentChange.getType() == DocumentChange.Type.ADDED){
                    try {
                        ChatMessage chatMessage = new ChatMessage();
                        chatMessage.senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                        chatMessage.receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                        chatMessage.message = documentChange.getDocument().getString(Constants.KEY_MESSAGE);
                        chatMessage.dateTime = getReadableDateTime((documentChange.getDocument().getTimestamp(Constants.KEY_TIMESTAMP).toDate()));
                        chatMessage.dateObject= documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                          //chatMessage.dateTime = SimpleDateFormat.getDateTimeInstance().format(new Date());
                         // chatMessage.dateObject= Calendar.getInstance().getTime();
                        chatMessages.add(chatMessage);
                    }catch (Exception e)
                    {
                        System.out.println("exception "+e.getMessage());
                    }

                }

            }

            Collections.sort(chatMessages,(obj1,obj2)-> obj1.dateObject.compareTo(obj2.dateObject));
            if(count == 0){
                chatAdapter.notifyDataSetChanged();
            }else{
                chatAdapter.notifyItemRangeInserted(chatMessages.size(),chatMessages.size());
                binding.chatScreenRv.setVisibility(chatMessages.size() -1);
            }
            binding.chatScreenRv.setVisibility(View.VISIBLE);
        }
        binding.chatProgressBar.setVisibility(View.GONE);
    };

private void loadReceiverDetails(){

        String s=getArguments().getString("rcv");
        rcvruserid=s;
        FirebaseDatabase.getInstance().getReference().child("User").child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                receiverUser=snapshot.getValue(User.class);
                receiverUser.setUid(s);
                //System.out.println("from database "+receiverUser.getName());
               // System.out.println(receiverUser.getPimage());
                binding.friendsName.setText(receiverUser.getName());
                Picasso.get().load(receiverUser.getPimage()).placeholder(R.drawable.ic_profile).into(binding.proImg);

                chatMessages=new ArrayList<>();
                //System.out.println(receiverUser.getPimage());
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

private String getReadableDateTime(Date date) {
        return new SimpleDateFormat("MMMM dd,yyyy - hh :mm a", Locale.getDefault()).format(date);
}

}
