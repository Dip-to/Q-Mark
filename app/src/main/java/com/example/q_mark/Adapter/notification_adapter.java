package com.example.q_mark.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.Model.Notification;
import com.example.q_mark.Model.User;
import com.example.q_mark.R;
import com.example.q_mark.commentActivity;
import com.example.q_mark.databinding.CommentSampleBinding;
import com.example.q_mark.databinding.NotificationBinding;
import com.example.q_mark.databinding.NotificationSampleBinding;
import com.example.q_mark.utilities.SendToUserProfile;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class notification_adapter extends RecyclerView.Adapter<notification_adapter.viewHolder> implements SendToUserProfile {

    Context context;
    ArrayList<Notification>list;

    public notification_adapter(Context context, ArrayList<Notification> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.notification_sample,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Notification model=list.get(position);

        String time= TimeAgo.using(model.getNotificationAt());
        holder.binding.notTime.setText(time);
        FirebaseDatabase.getInstance().getReference().child("User")
                .child(model.getNotificationBy()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user=snapshot.getValue(User.class);
                        Picasso.get().load(user.getPimage()).placeholder(R.drawable.ic_profile).into(holder.binding.proImg);
                        if(model.getType().equals("like"))
                        {
                            holder.binding.notText.setText(Html.fromHtml("<b>"+user.getName() +"</b >" +"     "+ "liked your post"));
                        }
                        else if(model.getType().equals("comment"))
                        {
                            holder.binding.notText.setText(Html.fromHtml("<b>"+user.getName() +"</b >" +"     "+ "Commented on your post."));
                        }
                        else if(model.getType().equals("follow"))
                        {
                            holder.binding.notText.setText(Html.fromHtml("<b>"+user.getName() +"</b >" +"     "+ "started following you"));
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        holder.binding.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(model.getType().equals("like") || model.getType().equals("comment"))
                {
                    FirebaseDatabase.getInstance().getReference().child("notification").child(model.getPostedBy())
                            .child(model.getNotificationId()).child("checkOpen").setValue(true);
                    holder.binding.clrs.setBackgroundColor(Color.WHITE);
                    Intent intent=new Intent(context, commentActivity.class);
                    intent.putExtra("postid",model.getPostId());
                    intent.putExtra("postedby",model.getPostedBy());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.goup,R.anim.godown);
                }
                else
                {
                    sendToProfile(view , model.getNotificationBy());
                }
            }
        });
        Boolean chck=model.isCheckOpen();
        if(chck==true)
        {
            holder.binding.clrs.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

       NotificationSampleBinding binding;
        public viewHolder(@NonNull View itemView) {

            super(itemView);
            binding = NotificationSampleBinding.bind(itemView);

        }
    }
}
