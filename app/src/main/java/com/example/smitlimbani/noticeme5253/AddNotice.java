package com.example.smitlimbani.noticeme5253;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.auth.viewmodel.RequestCodes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.prefs.NodeChangeEvent;

public class AddNotice extends AppCompatActivity {

    private Button mSelectGroupBtn;
    private static final int CAMERA_CAPTURE_CODE = 1;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private EditText mNoticeTitle;
    private EditText mExpiryDays;
    private EditText mNoticeContent;
    public String newNoticeKey;
    public Uri mImageUri;
    private Button mSaveDetails;
    public NoticeDetails noticeDetails = new NoticeDetails();

    public void setNewNoticeKey(String newNoticeKey) {
        this.newNoticeKey = newNoticeKey;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

        mNoticeTitle = (EditText) findViewById(R.id.ANnoticeTitle);
        mExpiryDays = (EditText) findViewById(R.id.ANdaysToExpire);
        mNoticeContent = (EditText) findViewById(R.id.ANnoticeContent);
        mSaveDetails = (Button) findViewById(R.id.ANsaveDetails);
        mSelectGroupBtn = (Button) findViewById(R.id.ANselectGroupsBtn);







        mSaveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                noticeDetails.setNotice_title(mNoticeTitle.getText().toString());
                noticeDetails.setNotice_expiry(mExpiryDays.getText().toString());
                noticeDetails.setNotice_content(mNoticeContent.getText().toString());
                noticeDetails.setNotice_sender(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());

                databaseReference.child("notice").push().setValue(noticeDetails, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        String noticeKey = databaseReference.getKey();
                        Log.e("andar valu", noticeKey);
                        setNewNoticeKey(noticeKey);
                        mSaveDetails.setEnabled(false);
                    }
                });
            }
        });


        mSelectGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectGroups();

            }
        });


    }

    private void selectGroups() {

        startActivity(new Intent(AddNotice.this, SelectGroups.class).putExtra("noticeId", newNoticeKey));
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
            Log.e("sdf",newNoticeKey);
//            mImageUri = data.getData();
//            filepath.putFile(mImageUri);

            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataBAOS = baos.toByteArray();

            StorageReference filepath = storageReference.child("notices").child(newNoticeKey);

            UploadTask uploadTask = filepath.putBytes(dataBAOS);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });
        }
    }
}
