package com.example.smitlimbani.noticeme5253;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrgUsersTypeHelper {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    DatabaseReference db = databaseReference.child("org_users_type");
    DatabaseReference org_users_type;


    public OrgUsersTypeHelper(String org_id) {
        org_users_type = db.child(org_id);
    }

    public ArrayList retrieve()
    {
        final ArrayList users = new ArrayList();

        org_users_type.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fetchData(dataSnapshot,users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        org_users_type.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Log.e("3","jsdfkd");
//                fetchData(dataSnapshot,users);
//                Log.e("4","jsdfkd");
//            }
//
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                Log.e("5","jsdfkd");
//                fetchData(dataSnapshot,users);
//                Log.e("6","jsdfkd");
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        return users;

    }

    private void fetchData(DataSnapshot snapshot,ArrayList<String> users)
    {
        users.clear();
        for(DataSnapshot ds:snapshot.getChildren())
        {

            String type = ds.getKey();

            Log.e("test", type);
            users.add(type);

        }

    }
}

