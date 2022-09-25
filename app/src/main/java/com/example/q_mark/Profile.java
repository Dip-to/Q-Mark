package com.example.q_mark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Profile extends Fragment {
    private TextView pro_name,email1,email2,mobile1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pro_name=getView().findViewById(R.id.name_profile);
        email1=getView().findViewById(R.id.email1);
        email2=getView().findViewById(R.id.email2);
        mobile1=getView().findViewById(R.id.mobile);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://q-mark-dd305-default-rtdb.firebaseio.com/User");
        FirebaseUser mUser= FirebaseAuth.getInstance().getCurrentUser();

        String s=mUser.getUid();
        fbase_userdata f=new fbase_userdata();
        ArrayList<String> pp= new ArrayList<String>();
        databaseReference.child(s).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    String name = String.valueOf(dataSnapshot.child("Name").getValue());
                    String email = String.valueOf(dataSnapshot.child("Email").getValue());
                    String mpbile = String.valueOf(dataSnapshot.child("Mobile").getValue());
                    String pimg = String.valueOf(dataSnapshot.child("Pimage").getValue());

                    pro_name.setText(name);
                    email1.setText(email);
                    email2.setText(email);
                    mobile1.setText(mpbile);

                }
            }
        });






    }
}
