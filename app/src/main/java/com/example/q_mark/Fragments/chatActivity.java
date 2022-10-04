package com.example.q_mark.Fragments;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.q_mark.Model.User;
import com.example.q_mark.databinding.ActivityChatScreenBinding;

public class chatActivity extends Fragment {

    private ActivityChatScreenBinding binding;
    private User receiverUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityChatScreenBinding.inflate(getLayoutInflater());
        setListener();
        loadReceiverDetails();
        return binding.getRoot();
    }
private void loadReceiverDetails(){
        receiverUser= (User) getActivity().getIntent().getSerializableExtra("user");
                binding.friendsName.setText(receiverUser.getName());
}

private void setListener(){
        binding.chatImageBack.setOnClickListener(view -> getActivity().onBackPressed());
}

}
