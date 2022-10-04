package com.example.q_mark.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.q_mark.Adapter.chat_users_adapter;
import com.example.q_mark.Listener.UserListener;
import com.example.q_mark.Model.User;
import com.example.q_mark.databinding.ChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Chat  extends Fragment implements UserListener {
    private ChatBinding chatBinding;

    ArrayList<User> list=new ArrayList<>();
    private Object User;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.chat,container,false);
        chatBinding= ChatBinding.inflate(inflater,container,false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        chatBinding.chatUserRecycleView.setLayoutManager(linearLayoutManager);

        chat_users_adapter chat_users_adapter=new chat_users_adapter(getContext(),list,this,this);
        chatBinding.chatUserRecycleView.setAdapter(chat_users_adapter);




        FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid())
                .child("following") .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for(DataSnapshot dataSnapshot: snapshot.getChildren())
                        {
                            FirebaseDatabase.getInstance().getReference().child("User").child(dataSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User user=snapshot.getValue(User.class);
                                    user.setUid(dataSnapshot.getKey());
                                    System.out.println(dataSnapshot.getKey());
                                    System.out.println(user.getName());
                                    list.add(user);
                                    chat_users_adapter.notifyDataSetChanged();


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }





                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


//        firebaseDatabase.getReference().child("User").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear();
//                for(DataSnapshot dataSnapshot: snapshot.getChildren())
//                {
//
//                    User user=dataSnapshot.getValue(User.class);
//                    user.setUid(dataSnapshot.getKey());
//                    System.out.println(dataSnapshot.getKey());
//                    if(!dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getUid()))
//                    {
//                        list.add(user);
//                    }
//                }
//                us.notifyDataSetChanged();
//            }
        return chatBinding.getRoot();

    }

//    @Override
//    public Fragment onUserClicked(User user) {
//        public static  chatActivity createInstance(ArrayList<User> list) {
//            chatActivity chat_fragment = new chatActivity();
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("user", list);
//            chat_fragment.setArguments(bundle);
//            return chat_fragment;
//        }
//
//    }


}
