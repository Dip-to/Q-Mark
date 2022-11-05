package com.example.q_mark.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.q_mark.Home;
import com.example.q_mark.R;
import com.example.q_mark.databinding.FragmentMiddlepageBinding;


public class MIddlepage extends Fragment {

    public static int on=0;
    FragmentMiddlepageBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentMiddlepageBinding.inflate(inflater,container,false);
        String s=getArguments().getString("university");
        binding.question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment ff=new University_content_search();
                //FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("university", s);
                bundle.putString("type","Question");
                ff.setArguments(bundle);
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,ff);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


//        getActivity().onBackPressed();
        //super.onBackPressed();


        binding.slides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment ff=new University_content_search();
                //FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("university", s);
                on=1;
                bundle.putString("type","Slide");
                ff.setArguments(bundle);
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,ff);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        binding.others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment ff=new University_content_search();
                //FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("university", s);
                bundle.putString("type","Others");
                ff.setArguments(bundle);
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,ff);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        binding.uniBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment ff=new Home();
                //FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,ff);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return binding.getRoot();
    }

}