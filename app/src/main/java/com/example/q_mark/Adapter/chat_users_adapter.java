package com.example.q_mark.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.Listener.UserListener;
import com.example.q_mark.Model.User;
import com.example.q_mark.R;
import com.example.q_mark.databinding.ItemContainerChatUserBinding;
import com.example.q_mark.utilities.SendToUserProfile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class chat_users_adapter extends RecyclerView.Adapter<chat_users_adapter.UserViewHolder> {

    Context context;
    ArrayList<User> users;
    private final UserListener userListener;

    public chat_users_adapter(Context context, ArrayList<User> users, UserListener userListener, UserListener userListener1) {
        this.context = context;
        this.users = users;
        this.userListener = userListener1;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_container_chat_user,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        //holder.setUsersData(users.get(position));
        User chatUser=users.get(position);
        holder.binding.chatTextName.setText(chatUser.getName());
        holder.binding.textEmail.setText(chatUser.getEmail());
        Picasso.get().load(chatUser.getPimage()).placeholder(R.drawable.ic_profile).into(holder.binding.chatImageprofile);
        holder.binding.getRoot().setOnClickListener(view -> userListener.onUserClicked(chatUser));

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{


        ItemContainerChatUserBinding binding;
        UserViewHolder(View itemContainerChatUserBinding){
            super(itemContainerChatUserBinding);
            binding = ItemContainerChatUserBinding.bind(itemContainerChatUserBinding);
        }

    }


}
