package com.example.q_mark.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.Model.User;
import com.example.q_mark.databinding.ItemContainerChatUserBinding;

import java.util.Base64;
import java.util.List;

public class chat_users_adapter extends RecyclerView.Adapter<chat_users_adapter.UserViewHolder> {

    private final List<User> users;

    public  chat_users_adapter(List<User> users){
        this.users=users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerChatUserBinding itemContainerChatUserBinding = ItemContainerChatUserBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        return new UserViewHolder(itemContainerChatUserBinding);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUsersData(users.get(position));

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{


        ItemContainerChatUserBinding binding;

        UserViewHolder(ItemContainerChatUserBinding itemContainerChatUserBinding){
            super(itemContainerChatUserBinding.getRoot());
            binding = itemContainerChatUserBinding;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        void setUsersData(User chatUser){
            binding.chatTextName.setText(chatUser.getName());
            binding.textEmail.setText(chatUser.getEmail());
            binding.chatImageprofile.setImageBitmap(getUserImage(chatUser.getPimage()));
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private Bitmap getUserImage(String encodedImage){
        byte[] bytes = Base64.getDecoder().decode(encodedImage);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);

    }

}
