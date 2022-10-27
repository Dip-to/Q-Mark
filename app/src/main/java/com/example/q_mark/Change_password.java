package com.example.q_mark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.q_mark.databinding.ActivityChangePasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class Change_password extends AppCompatActivity {
    EditText p1,p2,p3;
    Button button;
    ImageView back;
    private String s_pass1,s_pass2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        p2=findViewById(R.id.new_pass);
        p3=findViewById(R.id.confirm_pass);
        button=findViewById(R.id.update_btn);
        back=findViewById(R.id.change_pass_back_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                s_pass1=p2.getText().toString();
                s_pass2=p3.getText().toString();
                            if (s_pass1.isEmpty())
                            {
                                p2.setError("Password field can't be empty.");
                                System.out.println(s_pass1);
                            }
                            else if (s_pass1.length() < 6) p2.setError("Password length must be at least 6");
                            else if (!s_pass1.equals(s_pass2)) p3.setError("Password didn't match");
                            else
                            {
                                FirebaseAuth.getInstance().getCurrentUser().updatePassword(s_pass1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(getApplicationContext(), "Password changed successfully.", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(), "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }



            }
        });

    }
}