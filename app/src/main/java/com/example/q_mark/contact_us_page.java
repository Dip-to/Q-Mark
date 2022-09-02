package com.example.q_mark;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class contact_us_page extends Fragment {

    private EditText subject,msg;
    private Button send;
    private String[] email_tex= {"qmarkapp@gmail.com"};
    private ImageView cycle;
    Animation right_to_left;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.contact_us,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        subject=getView().findViewById(R.id.subject);
        msg=getView().findViewById(R.id.msg);
        send=getView().findViewById(R.id.send);
        cycle=getView().findViewById(R.id.cycle_man);
        right_to_left= AnimationUtils.loadAnimation(getActivity(),R.anim.right_to_left);

        cycle.setAnimation(right_to_left);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String subject_tex=subject.getText().toString();
               String msg_tex=msg.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SENDTO);
               // intent.setType("message/rfc822");
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL,email_tex);
                intent.putExtra(Intent.EXTRA_SUBJECT,subject_tex);
                intent.putExtra(Intent.EXTRA_TEXT,msg_tex);
                startActivity(intent);
            }
        });



    }
}
