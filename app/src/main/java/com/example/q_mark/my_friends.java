package com.example.q_mark;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.q_mark.Adapter.user_adapter;
import com.example.q_mark.Model.User;
import com.example.q_mark.databinding.MyFriendsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class my_friends extends AppCompatActivity {

    ArrayList<User> list=new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mauth;
    MyFriendsBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();


    }

}