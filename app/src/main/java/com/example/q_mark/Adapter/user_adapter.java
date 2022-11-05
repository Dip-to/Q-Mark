package com.example.q_mark.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.Model.Follower;
import com.example.q_mark.Model.Following;
import com.example.q_mark.Model.Notification;
import com.example.q_mark.R;
import com.example.q_mark.Model.User;
import com.example.q_mark.databinding.UserSampleBinding;
import com.example.q_mark.utilities.SendToUserProfile;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class user_adapter extends RecyclerView.Adapter<user_adapter.viewholder> implements SendToUserProfile {
    Context context;
    ArrayList<User> list;
    User curUser;

    public user_adapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_sample,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        User user=list.get(position);

        FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User tmpuser = snapshot.getValue(User.class);
                curUser = new User();
                curUser.setEmail(tmpuser.getEmail());
                curUser.setFollowerCount(tmpuser.getFollowerCount());
                curUser.setMobile(tmpuser.getMobile());
                curUser.setName(tmpuser.getName());
                curUser.setPimage(tmpuser.getPimage());
                curUser.setUniversity(tmpuser.getUniversity());
                curUser.setFollowingCount(tmpuser.getFollowingCount());
                curUser.setUid(FirebaseAuth.getInstance().getUid());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Picasso.get().load(user.getPimage()).placeholder(R.drawable.ic_profile).into(holder.binding.proImg);
        holder.binding.usName.setText(user.getName());
        holder.binding.usName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToProfile(view,user.getUid());
            }
        });
        holder.binding.proImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToProfile(view,user.getUid());
            }
        });

        holder.binding.puniv.setText(user.getUniversity());

        FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("followers").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    holder.binding.button.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.followdbutton));
                    holder.binding.button.setText("Following");
                    holder.binding.button.setTextColor(context.getResources().getColor(R.color.gray));
                }
                else
                {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        if(holder.binding.button.getText().toString().toLowerCase().equals("follow"))
//        {
//            holder.binding.button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Follower follower=new Follower();
//                    follower.setFollowedby(FirebaseAuth.getInstance().getUid());
//                    follower.setFollwedate(new Date().getTime());
//
//                    Following ff=new Following();
//                    ff.setFollow(user.getUid());
//                    ff.setFollowedate(new Date().getTime());
//
//                    FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("followers").child(curUser.getUid()).setValue(follower).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("followerCount").setValue(user.getFollowerCount()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        holder.binding.button.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.followdbutton));
//                                        holder.binding.button.setText("Following");
//                                        holder.binding.button.setTextColor(context.getResources().getColor(R.color.gray));
//
//                                        Toast.makeText(context, "You followed " +user.getName(), Toast.LENGTH_SHORT).show();
//
//
//                                        //notification
//                                        Notification notification=new Notification();
//                                        notification.setNotificationAt(new Date().getTime());
//                                        notification.setNotificationBy(FirebaseAuth.getInstance().getUid());
//                                        notification.setType("follow");
//
//                                        FirebaseDatabase.getInstance().getReference().child("notification").child(user.getUid()).push().setValue(notification).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void unused) {
//
//                                            }
//                                        });
//                                    }
//                                });
//                            }
//                    });
//                    FirebaseDatabase.getInstance().getReference().child("User").child(curUser.getUid()).child("following").child(ff.getFollow()).setValue(ff).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            FirebaseDatabase.getInstance().getReference().child("User").child(curUser.getUid()).child("followingCount").setValue(curUser.getFollowingCount()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//
//                                }
//                            });
//                        }
//                    });
//                }
//            });
//        }
//        else if(holder.binding.button.getText().toString().toLowerCase().equals("following"))
//        {
//            holder.binding.button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            User curuser = snapshot.getValue(User.class);
//                            //Decrease the follower count of the target userid and following count of the current user id
//                            FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("followerCount").setValue(Math.max(0,user.getFollowerCount()-1)).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//
//                                }
//                            });
//                            FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid()).child("followingCount").setValue(Math.max(0,curuser.getFollowingCount()-1)).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    //System.out.println(curUser.getName()+" has "+ curUser.getFollowingCount()+" followers");
//                                }
//                            });
//
//                            //remove the current user id from the targeted user id's follower and targeted user id from the current user id's following data
//                            FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("followers").child(FirebaseAuth.getInstance().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid()).child("following").child(user.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            holder.binding.button.setText("Follow");
//                                        }
//                                    });
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//            });
//        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        UserSampleBinding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding=UserSampleBinding.bind(itemView);

        }
    }
}
