package com.example.q_mark.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.q_mark.R;
import com.example.q_mark.StartActivity.Login;

public class Search_contents extends Fragment {
    Button uni;
    Button search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_search_contents,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uni=getView().findViewById(R.id.uni_content);
        search=getView().findViewById(R.id.srch_content);

        uni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_uni_content frag1=new button_uni_content();
                loadFragment(frag1);

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_srch_content frag2=new button_srch_content();
                loadFragment(frag2);
            }
        });
    }

    public void loadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_framelayout,fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

}
