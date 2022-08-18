package com.example.q_mark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import android.widget.TextView;

public class Otp_verify extends AppCompatActivity {

    private EditText otp_1,otp_2,otp_3,otp_4,otp_5,otp_6;
    private TextView resendbutton;
    private boolean resend_show=false;
    private int resendtime=1000;
    private int slectedpos=0;
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

        final Button verify=findViewById(R.id.verify_button);
        resendbutton = findViewById(R.id.resendotp);
        final TextView email=findViewById(R.id.otp_email);
        final TextView mobile=findViewById(R.id.otp_mobile);

        //getting email mobile form signup
        final String getEmail= getIntent().getStringExtra("email");
        final String getMobile= getIntent().getStringExtra("mobile");
        //setting email at otp page
        email.setText(getEmail);
        mobile.setText(getMobile);

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
                    //otp verify code
                }
            }
        });

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