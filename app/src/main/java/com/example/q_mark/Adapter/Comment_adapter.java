package com.example.q_mark.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.Model.Comment;
import com.example.q_mark.Model.User;
import com.example.q_mark.R;
import com.example.q_mark.databinding.CommentSampleBinding;
import com.example.q_mark.databinding.PostSampleBinding;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Comment_adapter extends RecyclerView.Adapter<Comment_adapter.viewHolder> {
    Context context;
    ArrayList<Comment> list;

    public Comment_adapter(Context context, ArrayList<Comment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.comment_sample,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Comment comment=list.get(position);
        String time= TimeAgo.using(comment.getCommentedAt());
        holder.binding.time.setText(time);
        FirebaseDatabase.getInstance().getReference().child("User").child(comment.getCommentedBy()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                holder.binding.pname.setText(Html.fromHtml("<b>"+user.getName() +"</b >" +"     "+comment.getCommentText()));
                Picasso.get().load(user.getPimage()).placeholder(R.drawable.ic_profile).into(holder.binding.pImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        CommentSampleBinding binding;
        public viewHolder(@NonNull View itemView) {

            super(itemView);
            binding = CommentSampleBinding.bind(itemView);

        }
    }
}
