package com.example.smitlimbani.noticeme5253;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.auth.viewmodel.RequestCodes;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddNotice extends AppCompatActivity {

    private Button mSelectGroupBtn;
    private static final int CAMERA_CAPTURE_CODE = 1;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private EditText mNoticeTitle;
    private EditText mExpiryDays;
    private EditText mNoticeContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

        mNoticeTitle = (EditText) findViewById(R.id.ANnoticeTitle);
        mExpiryDays = (EditText) findViewById(R.id.ANdaysToExpire);
        mNoticeContent = (EditText) findViewById(R.id.ANnoticeContent);

        mSelectGroupBtn = (Button) findViewById(R.id.ANselectGroupsBtn);

        mSelectGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectGroups();
            }
        });


    }

    private void selectGroups() {
        startActivity(new Intent(AddNotice.this, SelectGroups.class));
    }

    public void captureImage(View view)
    {
        Intent captureImg = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(captureImg, CAMERA_CAPTURE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== CAMERA_CAPTURE_CODE && resultCode == RESULT_OK)
        {
//            Uri uri= data.getData();
//            StorageReference filepath = storageReference.child("notices").child();
//            filepath.putFile(uri);
        }
    }
}
