package com.example.q_mark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {
    private String emailpattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private EditText password,email;
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private AppCompatButton login_button;
    private boolean pass_show=false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login_button=findViewById(R.id.login_bttn);
        progressDialog=new ProgressDialog(this);
        final TextView signUp = findViewById(R.id.sign_up_click);
        final ImageView showpass=findViewById(R.id.show_sup_pass);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                performlogin();

            }
        });
        showpass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(pass_show==true)
                {
                    pass_show=false;
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showpass.setImageResource(R.drawable.eye_show);
                }
                else
                {
                    pass_show=true;
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showpass.setImageResource(R.drawable.eye_hide);
                }
                password.setSelection(password.length());
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });


    }

    private void performlogin()
    {

        String s_email=email.getText().toString();
        String s_pass=password.getText().toString();
        if(!s_email.matches(emailpattern)) email.setError("Enter correct e-mail");
        else if(s_pass.isEmpty()) password.setError("Password field can't be empty.");
        else
        {
            progressDialog.setMessage("Please wait while login");
            progressDialog.setTitle("Log In");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(s_email,s_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        send_to_homepage();
                        Toast.makeText(Login.this, "Login successfull", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        try
                        {
                            throw task.getException();
                        } catch(Exception e)
                        {
                            Toast.makeText(getApplicationContext(), "Wrong Credential. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    private void send_to_homepage() {
        Intent intent=new Intent(Login.this,homepage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}