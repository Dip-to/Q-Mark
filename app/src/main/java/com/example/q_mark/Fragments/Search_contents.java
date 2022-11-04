package com.example.q_mark.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.q_mark.Adapter.VPadapter;
import com.example.q_mark.R;
import com.example.q_mark.StartActivity.Login;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class Search_contents extends Fragment {

   public static TabLayout tabLayout;
   private ViewPager2 viewPager;
   private ViewPager2 viewPager2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_search_contents,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout= getView().findViewById(R.id.table_layout);
        viewPager= getView().findViewById(R.id.viewpager);



//        tabLayout.setupWithViewPager(viewPage);
        button_uni_content frag1=new button_uni_content();
        button_srch_content frag2=new button_srch_content();

        VPadapter vPadapter =new VPadapter(getActivity().getSupportFragmentManager(),getLifecycle());
        viewPager.setAdapter(vPadapter);
       // vPadapter.addFragment(frag1,"UNIVERSITY CONTENT");
        tabLayout.addTab(tabLayout.newTab().setText("UNIVERSITY CONTENT"));
        tabLayout.addTab(tabLayout.newTab().setText("SEARCH CONTENT"));
//
//        vPadapter.notifyDataSetChanged();
//        vPadapter.addFragment(frag2,"SEARCH CONTENT");
//        vPadapter.notifyDataSetChanged();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
}
