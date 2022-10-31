package com.example.q_mark;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.UUID;

public class Cropclass extends AppCompatActivity {

    String result;
    Uri fileuri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropclass);

        readIntent();
        String des=new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();
        UCrop.Options options=new UCrop.Options();

        UCrop.of(fileuri,Uri.fromFile(new File(getCacheDir(),des))).withOptions(options)
                .withAspectRatio(0,0).useSourceImageAspectRatio().withMaxResultSize(2000,2000).start(Cropclass.this);
    }

    private void readIntent() {
        Intent intent=getIntent();
        if(intent.getExtras()!=null)
        {
            result=intent.getStringExtra("Data");
            fileuri=Uri.parse(result);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("chck11111");
        if(resultCode==RESULT_OK && requestCode==UCrop.REQUEST_CROP)
        {

            final Uri ruri=UCrop.getOutput(data);
            Intent in=new Intent();
            in.putExtra("result",ruri+"");
            System.out.println("image going here : "+ruri.toString());
            setResult(-1,in);
            finish();

        }
    }
}