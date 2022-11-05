package com.example.q_mark;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.Adapter.post_adapter;
import com.example.q_mark.Model.Post;
import com.example.q_mark.Model.User;
import com.example.q_mark.utilities.SendToUserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Home extends Fragment{

    ImageView addpost;
    RecyclerView rv;
    ArrayList<Post> dashboardList;
    ImageView img;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addpost=getView().findViewById(R.id.addpost);
        rv=getView().findViewById(R.id.rv);
        img=getView().findViewById(R.id.profile);
        FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                Picasso.get().load(user.getPimage()).placeholder(R.drawable.ic_profile).into(img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //timeline work
        dashboardList=new ArrayList<>();
        post_adapter pp=new post_adapter(dashboardList,getContext());
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager);
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(),DividerItemDecoration.VERTICAL));
        rv.setNestedScrollingEnabled(false);
        rv.setAdapter(pp);
        Map<String, Boolean> map=new HashMap<String,Boolean>();
        map.put(FirebaseAuth.getInstance().getUid(),true);
        FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid()).child("following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    User user=dataSnapshot.getValue(User.class);
                    user.setUid(dataSnapshot.getKey());
                    System.out.println("uid"+user.getUid());
                    map.put(user.getUid(),true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("posts").orderByChild("postedAt").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dashboardList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Post post=dataSnapshot.getValue(Post.class);
                    post.setPostID(dataSnapshot.getKey());
                    if(map.containsKey(post.getPostedBy())==true)
                    {
                        dashboardList.add(post);
                    }
                }

                Collections.reverse(dashboardList);
                pp.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Add_post_activity.class);
                startActivity(intent);
            }
        });


    }
}
