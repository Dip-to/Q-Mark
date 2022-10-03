package com.example.q_mark.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.q_mark.databinding.ChatBinding;


public class Chat  extends Fragment {
    private ChatBinding chatBinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.chat,container,false);
        chatBinding= ChatBinding.inflate(inflater,container,false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        chatBinding.chatUserRecycleView.setLayoutManager(linearLayoutManager);
        return chatBinding.getRoot();

    }
}
