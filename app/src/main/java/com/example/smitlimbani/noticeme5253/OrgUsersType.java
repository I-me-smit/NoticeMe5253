package com.example.smitlimbani.noticeme5253;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class OrgUsersType extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseReference db;
    OrgUsersTypeHelper helper;
    private String addquery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_users_type);

        Log.e("7","jsdfkd");

        Spinner sp = (Spinner) findViewById(R.id.spinner2);
//        db = FirebaseDatabase.getInstance().getReference().child("user_details").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("org_id");
//
//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String org_id = dataSnapshot.getValue().toString();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

         helper = new OrgUsersTypeHelper(getIntent().getExtras().getString("org_id").toString());

       ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, helper.retrieve());
        //ArrayAdapter<String> arrayAdapter = ArrayAdapter.createFromResource(this,helper.retrieve(),android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp.setAdapter(arrayAdapter);
        sp.setOnItemSelectedListener(this);
        Log.e("8","jsdfkd");
        Button btn = (Button) findViewById(R.id.btnSubmit);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }
}

