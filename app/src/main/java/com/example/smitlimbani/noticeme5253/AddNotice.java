package com.example.smitlimbani.noticeme5253;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AddNotice extends AppCompatActivity {

    private Button mSelectGroupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

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
        startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
    }
}
