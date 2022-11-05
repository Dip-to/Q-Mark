package com.example.q_mark.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.q_mark.Model.Follower;
import com.example.q_mark.Model.Following;
import com.example.q_mark.Model.Notification;
import com.example.q_mark.Model.User;
import com.example.q_mark.R;
import com.example.q_mark.databinding.UserProfileBinding;
import com.example.q_mark.databinding.UserSampleBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class Profile_user extends Fragment {
    UserProfileBinding binding;
    User curUser,suser;
    String s;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=UserProfileBinding.inflate(inflater,container,false);
        s=getArguments().getString("id");
        if(s.equals(FirebaseAuth.getInstance().getUid()))
        {
            binding.fb.setVisibility(View.GONE);
        }
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
        FirebaseDatabase.getInstance().getReference().child("User").child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                user.setUid(snapshot.getKey());

                User tmpuser = snapshot.getValue(User.class);
                suser = new User();
                suser.setEmail(tmpuser.getEmail());
                suser.setFollowerCount(tmpuser.getFollowerCount());
                suser.setMobile(tmpuser.getMobile());
                suser.setName(tmpuser.getName());
                suser.setPimage(tmpuser.getPimage());
                suser.setUniversity(tmpuser.getUniversity());
                suser.setFollowingCount(tmpuser.getFollowingCount());
                suser.setUid(s);


                binding.nameProfile.setText(user.getName());
                binding.univ.setText(user.getUniversity());
                binding.profileEmail.setText(user.getEmail());
                binding.mobile.setText(user.getMobile());
                binding.followerCount.setText(String.valueOf(user.getFollowerCount()));
                binding.followingCount.setText(String.valueOf(user.getFollowingCount()));
                Picasso.get().load(user.getPimage()).placeholder(R.drawable.ic_profile).into(binding.proImg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid()).child("following")
                        .child(getArguments().getString("id")).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            binding.fb.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.followdbutton));
                            binding.fb.setText("Following");
                            binding.fb.setTextColor(getContext().getResources().getColor(R.color.gray));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        binding.fb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(binding.fb.getText().toString().toLowerCase().equals("follow"))
                    {
                        Follower follower=new Follower();
                        follower.setFollowedby(FirebaseAuth.getInstance().getUid());
                        follower.setFollwedate(new Date().getTime());

                        Following ff=new Following();
                        ff.setFollow(suser.getUid());
                        ff.setFollowedate(new Date().getTime());

                        FirebaseDatabase.getInstance().getReference().child("User").child(suser.getUid()).child("followers").child(curUser.getUid()).setValue(follower).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                FirebaseDatabase.getInstance().getReference().child("User").child(suser.getUid()).child("followerCount").setValue(suser.getFollowerCount()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        binding.fb.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.followdbutton));
                                        binding.fb.setText("Following");
                                        binding.fb.setTextColor(getContext().getResources().getColor(R.color.gray));

                                        Toast.makeText(getContext(), "You followed " +suser.getName(), Toast.LENGTH_SHORT).show();


                                        //notification
                                        Notification notification=new Notification();
                                        notification.setNotificationAt(new Date().getTime());
                                        notification.setNotificationBy(FirebaseAuth.getInstance().getUid());
                                        notification.setType("follow");

                                        FirebaseDatabase.getInstance().getReference().child("notification").child(suser.getUid()).push().setValue(notification).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });
                                    }
                                });
                            }
                        });
                        FirebaseDatabase.getInstance().getReference().child("User").child(curUser.getUid()).child("following").child(ff.getFollow()).setValue(ff).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                FirebaseDatabase.getInstance().getReference().child("User").child(curUser.getUid()).child("followingCount").setValue(curUser.getFollowingCount()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                });
                            }
                        });

                    }
                    else if(binding.fb.getText().toString().toLowerCase().equals("following"))
                    {
                        FirebaseDatabase.getInstance().getReference().child("User").child(suser.getUid()).child("followers").child(curUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                FirebaseDatabase.getInstance().getReference().child("User").child(suser.getUid()).child("followerCount").setValue(suser.getFollowerCount()-1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        binding.fb.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.followdbutton));
                                        binding.fb.setText("Follow");
                                        binding.fb.setTextColor(getContext().getResources().getColor(R.color.black));

                                        Toast.makeText(getContext(), "You Unfollowed " +suser.getName(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                                }
                        });

                        FirebaseDatabase.getInstance().getReference().child("User").child(curUser.getUid()).child("following").child(s).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                FirebaseDatabase.getInstance().getReference().child("User").child(curUser.getUid()).child("followingCount").setValue(curUser.getFollowingCount()-1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                });

                                }
                        });





                        }


                }
            });



        return binding.getRoot();
    }
}
