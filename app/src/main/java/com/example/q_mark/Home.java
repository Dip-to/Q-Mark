package com.example.q_mark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.q_mark.Fragments.add_post;
import com.example.q_mark.Fragments.me_following;

public class Home extends Fragment {

    Button addpost;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addpost=getView().findViewById(R.id.addpost);
        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment addp=new add_post();
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,addp).commit();
            }
        });

    }
}
