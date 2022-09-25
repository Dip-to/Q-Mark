package com.example.q_mark;

import androidx.annotation.NonNull;

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

public class fbase_userdata {

   public ArrayList<String> fdata(String s) {
      //FirebaseUser mUser= FirebaseAuth.getInstance().getCurrentUser();
      // String s=mUser.getUid();
      ArrayList<String> pp = new ArrayList<String>();
      DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://q-mark-dd305-default-rtdb.firebaseio.com/User");
      databaseReference.child(s).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
         @Override
         public void onComplete(@NonNull Task<DataSnapshot> task) {
            if (task.isSuccessful()) {
               DataSnapshot dataSnapshot = task.getResult();
               String name = String.valueOf(dataSnapshot.child("Name").getValue());
               String email = String.valueOf(dataSnapshot.child("Email").getValue());
               String mpbile = String.valueOf(dataSnapshot.child("Mobile").getValue());
               String pimg = String.valueOf(dataSnapshot.child("Pimage").getValue());

               pp.add(name);
               pp.add(email);
               pp.add(mpbile);
               pp.add(pimg);

            }
         }
      });
      return pp;
   }
}
