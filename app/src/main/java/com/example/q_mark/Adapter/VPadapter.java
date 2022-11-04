package com.example.q_mark.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.q_mark.Fragments.Search_contents;
import com.example.q_mark.Fragments.button_srch_content;
import com.example.q_mark.Fragments.button_uni_content;
import com.example.q_mark.R;

import java.util.ArrayList;

public class VPadapter extends FragmentStateAdapter {

    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    public VPadapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 1:
                return new button_srch_content();
            case 2:
                return new button_uni_content();
        }

        return new button_uni_content();
    }
    @Override
    public int getItemCount() {
        return 2;
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public ArrayList<Fragment> getFragmentArrayList() {
        return fragmentArrayList;
    }
}
