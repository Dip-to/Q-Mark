package com.example.q_mark.utilities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.q_mark.Fragments.Profile_user;
import com.example.q_mark.R;

public interface SendToUserProfile {
    default void sendToProfile(View view, String key)
    {
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        Fragment ff=new Profile_user();
        Bundle bundle = new Bundle();
        bundle.putString("id", key);
        ff.setArguments(bundle);
        FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,ff);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
