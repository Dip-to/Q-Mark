package com.example.q_mark.Fragments;

import java.io.*;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.q_mark.MainActivity;
import com.example.q_mark.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;
import android.app.ProgressDialog;

public class Upload extends Fragment {
    //constant to track image chooser intent
    private static final int PICK_IMAGE_REQUEST = 234 , PICK_PDF_REQUEST=123;

    private Button UploadPageChooseFileButton,UploadPageUploadButton,pdfUpload;
    private TextView UploadPageChooseFileTextView;
    private ImageView imageView;

    //uri to store file
    private Uri imgfilePath,pdffilePath;

    //firebase objects
    FirebaseAuth mauth;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mauth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseStorage=firebaseStorage.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.upload, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //view objects
        UploadPageChooseFileButton = (Button) getView().findViewById(R.id.choose_btn);
        UploadPageUploadButton = (Button) getView().findViewById(R.id.upload_btn);
        UploadPageChooseFileTextView = (TextView) getView().findViewById(R.id.upload_txtfield);
        imageView = (ImageView) getView().findViewById(R.id.uploadedImage);
        pdfUpload = (Button) getView().findViewById(R.id.choosepdfbtn);
        firebaseStorage = FirebaseStorage.getInstance();

        UploadPageChooseFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageFileChooser();
            }
        });
        pdfUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPdfFileChooser();
            }
        });

        UploadPageUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });
    }

    private void uploadFile() {
        //if there is a file to upload
        if (imgfilePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            final StorageReference storageReference = firebaseStorage.getReference().child(FirebaseAuth.getInstance().getUid()).child("Photo").child(new Date().getTime()+" ");
            storageReference.putFile(imgfilePath);
            progressDialog.dismiss();
        }
        //if there is not any file
        else {
            //you can display an error toast
        }

        if(pdffilePath!=null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            final StorageReference storageReference = firebaseStorage.getReference().child(FirebaseAuth.getInstance().getUid()).child("Pdf").child(new Date().getTime()+" ");
            storageReference.putFile(pdffilePath);
            progressDialog.dismiss();
        }
    }

    private void showImageFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an Image"),PICK_IMAGE_REQUEST);
    }
    private void showPdfFileChooser() {
        Intent intent = new Intent();
        intent.setType("pdf/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an Pdf"),PICK_PDF_REQUEST);
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data!=null && data.getData() != null)
        {
            imgfilePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgfilePath);
                imageView.setImageBitmap(bitmap);
                UploadPageChooseFileTextView.setText(getFileName(imgfilePath));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(requestCode == PICK_PDF_REQUEST && resultCode == Activity.RESULT_OK && data!=null && data.getData() != null)
        {
            pdffilePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), pdffilePath);
                //imageView.setImageBitmap(bitmap);
                //UploadPageChooseFileTextView.setText(getFileName(imgfilePath));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
