package com.example.q_mark.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.Adapter.notification_adapter;
import com.example.q_mark.Model.Notification;
import com.example.q_mark.R;
import com.example.q_mark.databinding.NotificationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class notification extends Fragment {

    ArrayList<Notification> list=new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mauth;
    RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notification,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv=getView().findViewById(R.id.rv);
        notification_adapter ff= new notification_adapter(getContext(),list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(ff);

        FirebaseDatabase.getInstance().getReference().child("notification").child(FirebaseAuth.getInstance().getUid()).orderByChild("notificationAt")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for(DataSnapshot dataSnapshot: snapshot.getChildren())
                        {
                            Notification notification=dataSnapshot.getValue(Notification.class);
                            notification.setNotificationId(dataSnapshot.getKey());
                            System.out.println(notification.getNotificationId());
                            list.add(notification);
                        }
                        Collections.reverse(list);
                        ff.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
