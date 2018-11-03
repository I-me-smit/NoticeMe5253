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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class OrgUsersType extends AppCompatActivity  {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference ref ;
    OrgUsersTypeHelper helper;
    private String addquery;
    ListView orgUserType_list;
    ArrayList<String> list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_users_type);
        Bundle bundle = getIntent().getExtras();
        String orgid = getIntent().getExtras().getString("org_id").toString();

//        Log.e("7", "jsdfkd");

        orgUserType_list = (ListView) findViewById(R.id.orgUserType_list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,list );
        orgUserType_list.setAdapter(adapter);
        orgUserType_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //Query query = ref.child("org_users_type");
        ref = databaseReference.child("org_users_type").child(orgid);
//

//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds : dataSnapshot.getChildren())
//                {
//
//                    Log.e("11", "bbbbbjsdfkd");
//
//
//                list.add(dataSnapshot.getValue().toString());
//                adapter.notifyDataSetChanged();
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//        Log.e("9", "aaaaa jsdfkdbjhscjhbcj");
        orgUserType_list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        String userType = orgUserType_list.getItemAtPosition(position).toString();
                        databaseReference.child("user_details").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("user_type").setValue(userType);

//                        Log.e("912", "selected");

                    }
                });
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                adapter.add(
//                        (String) dataSnapshot.getValue());

                list.add(dataSnapshot.getKey().toString());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                list.remove(dataSnapshot.getValue(String.class));
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
//            ref.addChildEventListener(childListener);


        });


    }
//        Spinner sp = (Spinner) findViewById(R.id.spinner2);
//        db = FirebaseDatabase.getInstance().getReference().child("user_details").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("org_id");
//
//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String org_id = dataSnapshot.  Value().toString();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//         helper = new OrgUsersTypeHelper(getIntent().getExtras().getString("org_id").toString());
//
//
//        Log.e("8","jsdfkd");
//        Button btn = (Button) findViewById(R.id.btnSubmit);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//            }
//        });





}

