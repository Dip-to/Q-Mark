package com.example.q_mark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Otp_verify extends AppCompatActivity {

    private EditText otp_1,otp_2,otp_3,otp_4,otp_5,otp_6;
    private TextView resendbutton;
    private boolean resend_show=false;
    private int resendtime=1000;
    private int slectedpos=0;
    private String verification_otp;
    private ProgressBar progressBar;
    FirebaseAuth mAuth,eauth;
    FirebaseUser mUser;
    ProgressDialog progressDialog;
    private String getEmail,getName,getMobile,getPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_otp_verify);



        otp_1=findViewById(R.id.otp_digit_1);
        otp_2=findViewById(R.id.otp_digit_2);
        otp_3=findViewById(R.id.otp_digit_3);
        otp_4=findViewById(R.id.otp_digit_4);
        otp_5=findViewById(R.id.otp_digit_5);
        otp_6=findViewById(R.id.otp_digit_6);
        progressBar=findViewById(R.id.progressBar2);
        progressDialog=new ProgressDialog(this);
        final Button verify=findViewById(R.id.verify_button);
        resendbutton = findViewById(R.id.resendotp);
        final TextView mobile=findViewById(R.id.otp_mobile);

        //getting data form signup
        getEmail= getIntent().getStringExtra("email");
        getMobile= getIntent().getStringExtra("mobile");
        getName=getIntent().getStringExtra("name");
        getPassword=getIntent().getStringExtra("password");
        verification_otp=getIntent().getStringExtra("otp");

        //setting phoneat otp page
        mobile.setText(String.format("+88%s",getMobile));

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        otp_1.addTextChangedListener(textwatcher);
        otp_2.addTextChangedListener(textwatcher);
        otp_3.addTextChangedListener(textwatcher);
        otp_4.addTextChangedListener(textwatcher);
        otp_5.addTextChangedListener(textwatcher);
        otp_6.addTextChangedListener(textwatcher);

        show_keyboard(otp_1);

        reset_tym_count();
        resendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(resend_show==true)
                {

                    //send otp agian code

                    reset_tym_count();
                    otpsendagain();
                }
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String otp= otp_1.getText().toString()+otp_2.getText().toString()+otp_3.getText().toString()+otp_4.getText().toString()+
                        otp_5.getText().toString()+otp_6.getText().toString();
                if(otp.length()==6)
                {
                    progressBar.setVisibility(view.VISIBLE);
                    PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(verification_otp,otp);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(view.GONE);
                            if(task.isSuccessful())
                            {

                                progressDialog.setMessage("Registration in progress");
                                progressDialog.setTitle("Registration");
                                progressDialog.setCanceledOnTouchOutside(false);
                                progressDialog.show();
                                mAuth.createUserWithEmailAndPassword(getEmail,getPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<AuthResult> task)
                                                   {

                                                       if(task.isSuccessful())
                                                       {
                                                           progressDialog.dismiss();
                                                           Toast.makeText(Otp_verify.this, "Registration successfull", Toast.LENGTH_SHORT).show();
                                                           Intent intent=new Intent(Otp_verify.this,homepage.class);
                                                           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                           startActivity(intent);
                                                       }
                                                       else
                                                       {
                                                           progressDialog.dismiss();
                                                           try
                                                           {
                                                               throw task.getException();
                                                           } catch(Exception e)
                                                           {
                                                               Toast.makeText(getApplicationContext(), "Email already taken! Try login", Toast.LENGTH_SHORT).show();
                                                           }
                                                       }
                                                   }
                                               });

                            }
                            else
                            {
                                progressBar.setVisibility(view.GONE);
                                Toast.makeText(Otp_verify.this, "Enter Correct OTP.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
                else
                {
                    Toast.makeText(Otp_verify.this, "Please Enter All digits.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void otpsendagain() {
        PhoneAuthOptions options =  PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+88"+getMobile)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks
                        (
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                                {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {

                                        Toast.makeText(Otp_verify.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        verification_otp=s;
                                    }
                                }
                        )          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void show_keyboard(EditText otp)
    {
        otp.requestFocus();
        InputMethodManager inputMethodManager=( InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otp,InputMethodManager.SHOW_IMPLICIT);

    }
   private void reset_tym_count()
   {
       resend_show=false;
       resendbutton.setTextColor(Color.parseColor("#99000000"));
       new CountDownTimer(resendtime*60,1000){

           @Override
           public void onTick(long l) {
               resendbutton.setText("Resend Code (" +(l/1000)+")");
           }

           @Override
           public void onFinish() {
               resend_show=true;
               resendbutton.setText("Resend Code");
               resendbutton.setTextColor(Color.parseColor("#2B91E1"));

           }
       }.start();
   }


    private final TextWatcher textwatcher= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(editable.length()>0)
            {
                if(slectedpos==0)
                {
                    slectedpos=1;
                    show_keyboard(otp_2);
                }
                else if(slectedpos==1)
                {
                    slectedpos=2;
                    show_keyboard(otp_3);

                }
                else if(slectedpos==2)
                {
                    slectedpos=3;
                    show_keyboard(otp_4);
                }
                else if(slectedpos==3)
                {
                    slectedpos=4;
                    show_keyboard(otp_5);
                }else if(slectedpos==4)
                {
                    slectedpos = 5;
                    show_keyboard(otp_6);
                }
            }
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_DEL)
        {
            if(slectedpos==5)
            {
                slectedpos=4;
                show_keyboard(otp_5);
            }
            else if(slectedpos==4)
            {
                slectedpos=3;
                show_keyboard(otp_4);
            }
            else if(slectedpos==3)
            {
                slectedpos=2;
                show_keyboard(otp_3);
            }
            else if(slectedpos==2)
            {
                slectedpos=1;
                show_keyboard(otp_2);
            }
            else if(slectedpos==1)
            {
                slectedpos=0;
                show_keyboard(otp_1);
            }
            return  true;
        }
        else return super.onKeyUp(keyCode, event);

    }
}