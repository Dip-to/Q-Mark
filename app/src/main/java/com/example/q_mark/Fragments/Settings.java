package com.example.q_mark.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.q_mark.R;
import com.example.q_mark.StartActivity.Login;
import com.example.q_mark.StartActivity.Welcome_page;
import com.google.firebase.auth.FirebaseAuth;

public class Settings extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent=new Intent(getActivity(), Login.class);
        startActivity(intent);
        Toast.makeText(getContext(), "Log Out successful.", Toast.LENGTH_SHORT).show();
    }
}
