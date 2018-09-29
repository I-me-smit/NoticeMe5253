package com.example.smitlimbani.noticeme5253;

import android.app.Dialog;
import android.content.Context;
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
    private Spinner sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_users_type);


        sp = (Spinner) findViewById(R.id.mcSpinner);

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

        sp.setAdapter(arrayAdapter);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp.setOnItemSelectedListener(this);



        Log.e("sfd","jhkh");





        Log.e("8","jsdfkd");
        Button btn = (Button) findViewById(R.id.btnSubmit);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });


    }

    private void add() {

//        Dialog d=new Dialog(this);
//        d.setTitle("Firebase database");
//        d.setContentView(R.layout.input_dialog);
//
//        final EditText nameTxt= (EditText) d.findViewById(R.id.nameEditText);
//        Button saveBtn= (Button) d.findViewById(R.id.saveBtn);
//
//        //SAVE
//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //GET DATA
//                String name=nameTxt.getText().toString();
//
//                //set data
//                Spacecraft s=new Spacecraft();
//                s.setName(name);
//
//                //SAVE
//                if(name != null && name.length()>0)
//                {
//                    if(helper.save(s))
//                    {
//                        nameTxt.setText("");
//                    }
//
//                }else
//                {
//                    Toast.makeText(MainActivity.this, "Name Cannot Be Empty", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//
//        d.show();
//    }
//
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Log.e("test2", String.valueOf(i));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.e("nai", "thatu");
    }
}

