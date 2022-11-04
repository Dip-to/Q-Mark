package com.example.q_mark.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.q_mark.Model.User;
import com.example.q_mark.R;
import com.example.q_mark.Search;
import com.example.q_mark.utilities.SendToUserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchUsersAdapter extends RecyclerView.Adapter<SearchUsersAdapter.ViewHolder> implements SendToUserProfile {
    private Context mcontext;
    private List<User> muser;
    String TAG = "SearchUserAdapter";

    private FirebaseUser firebaseUser;
    public SearchUsersAdapter(Context mcontext, List<User> muser) {
        this.mcontext = mcontext;
        this.muser = muser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.user_search_items,parent,false);
        return new SearchUsersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchUsersAdapter.ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final User users = muser.get(position);
        holder.username.setText(users.getName());
        holder.fullname.setText(users.getName());
        Glide.with(mcontext).load(users.getPimage()).into(holder.profileimage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(TAG, "Users Profile UID "+users.getUid());
                Intent intent = new Intent(mcontext , Search.class);
                intent.putExtra("SearchedUserid",users.getUid());
                mcontext.startActivity(intent);
            }
        });
        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToProfile(view,users.getUid());
            }
        });
        holder.profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToProfile(view,users.getUid());
            }
        });
    }

    @Override
    public int getItemCount() {
        return muser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username,fullname;
        public CircleImageView profileimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fullname = (TextView)itemView.findViewById(R.id.fullName);
            profileimage = (CircleImageView)itemView.findViewById(R.id.user_img);
        }
    }
}

