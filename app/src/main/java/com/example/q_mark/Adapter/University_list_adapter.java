package com.example.q_mark.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.Fragments.MIddlepage;
import com.example.q_mark.Fragments.Profile_user;
import com.example.q_mark.Fragments.Search_contents;
import com.example.q_mark.Fragments.me_following;
import com.example.q_mark.Model.User;
import com.example.q_mark.R;
import com.example.q_mark.databinding.UnvListSampleBinding;
import com.example.q_mark.databinding.UserSampleBinding;

import java.util.ArrayList;

public class University_list_adapter extends RecyclerView.Adapter<University_list_adapter.viewholder> {
    Context context;
    ArrayList<String> list;

    public University_list_adapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.unv_list_sample,parent,false);
        return new University_list_adapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        String s=list.get(position);
        holder.binding.nm.setText(s);

        holder.binding.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Search_contents.tabLayout.setVisibility(View.GONE);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment ff=new MIddlepage();
                //FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("university", s);
                bundle.putString("jhamela","ja");
                ff.setArguments(bundle);
                FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,ff);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        UnvListSampleBinding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding=UnvListSampleBinding.bind(itemView);


        }
    }
}
