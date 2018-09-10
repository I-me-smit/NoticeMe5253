package com.example.smitlimbani.noticeme5253;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AddSubGroup extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private RecyclerView mMemberList;
    private FirebaseRecyclerAdapter<UserDetails, MembersViewHolder> firebaseRecyclerAdapter;
    private EditText mGroupName;
    private EditText mSearchAdmin;
    private ImageButton mSearchBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub_group);

        mMemberList = (RecyclerView) findViewById(R.id.ASGmembers);
        mGroupName = (EditText) findViewById(R.id.ASGgroupName);
        mSearchAdmin =(EditText) findViewById(R.id.ASGsearchAdmin);
        mSearchBtn = (ImageButton) findViewById(R.id.ASGsearchButton);

        mMemberList.setHasFixedSize(true);


        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchForAdmin(mSearchAdmin.getText().toString());
            }
        });



    }

    private void searchForAdmin(String searchString) {

        Log.e("ye", "hua");

        Query query = databaseReference.child("user_details").orderByChild("displayName").startAt(searchString).endAt(searchString + "\uf8ff");

        FirebaseRecyclerOptions<UserDetails> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<UserDetails>()
                .setQuery(query, UserDetails.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UserDetails, MembersViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull final MembersViewHolder holder, int position, @NonNull final UserDetails model) {
                holder.mDisplayName.setText(model.getDisplayName());
                holder.mContactNo.setText(model.getContactNo());
                holder.mEmailId.setText(model.getEmailId());

                holder.mMemberCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setGroupDetails(getRef(holder.getAdapterPosition()).getKey());
                        Log.e("enter", "hua");
                        Toast.makeText(getApplicationContext(), model.getDisplayName() + " added to the group", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }

            @NonNull
            @Override
            public MembersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_card, parent, false);
                return new MembersViewHolder(view);
            }

        };

        mMemberList.setLayoutManager(new LinearLayoutManager(this));
        firebaseRecyclerAdapter.startListening();
        mMemberList.setAdapter(firebaseRecyclerAdapter);
    }

    public void setGroupDetails(String adminUserId){
        final GroupDetails groupDetails = new GroupDetails();

        groupDetails.setGroup_admin(adminUserId);
        groupDetails.setGroup_name(mGroupName.getText().toString());
        DatabaseReference orgIdRef = databaseReference.child("user_details").child(adminUserId).child("org_id");
        orgIdRef.addListenerForSingleValueEvent(new ValueEventListener() {

            String orgId;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                orgId=dataSnapshot.getValue().toString();

                DatabaseReference orgNameRef = databaseReference.child("organizations").child(orgId).child("org_name");

                orgNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    public String orgName;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.e("value",orgId );

                        orgName = dataSnapshot.getValue().toString();

                        groupDetails.setOrg_id(orgId);
                        groupDetails.setOrg_name(orgName);

                        Log.e("id",groupDetails.getOrg_id() );
                        Log.e("name",groupDetails.getOrg_name() );
                        DatabaseReference groupRef = databaseReference.child("groups");
                        groupRef.push().setValue(groupDetails, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                String newGroupKey = databaseReference.getKey();
                                firebaseDatabase.getReference().child("can_post_to").child(getIntent().getExtras().getString("group_id").toString()).child(newGroupKey).setValue(true);
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



//        Log.e("id2",groupDetails.getOrg_id() );
//        Log.e("name2",groupDetails.getOrg_name() );
//

    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_sub_group);
//
//        mSearch = (ImageButton) findViewById(R.id.searchGroupBtn);
//
//        mSearchBox = (EditText) findViewById(R.id.searchGroupBox);
//
//        mGroupsList = (RecyclerView) findViewById(R.id.searchGroupList);
//        mGroupsList.setHasFixedSize(true);
//
//        mSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                searchQuery = mSearchBox.getText().toString();
//                userSearch(searchQuery);
//            }
//        });
//
//    }
//
//    public void userSearch(String searchString) {
//        Query query = databaseReference.child("groups").orderByChild("group_name").startAt(searchString).endAt(searchString + "\uf8ff");
//
//        FirebaseRecyclerOptions<GroupDetails> options = new FirebaseRecyclerOptions.Builder<GroupDetails>()
//                .setQuery(query, GroupDetails.class)
//                .build();
//
//        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<GroupDetails, GroupViewHolder>(options) {
//            @NonNull
//            @Override
//            public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_group, parent, false);
//                return new GroupViewHolder(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull final GroupViewHolder holder, final int position, @NonNull final GroupDetails model) {
//
//                holder.mGroupName.setText(model.getGroup_name().toString());
//                holder.mOrgName.setText(model.getOrg_name().toString());
//
//                holder.mGroupName.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        databaseReference.child("can_post_to").child(getIntent().getExtras().getString("gid")).child(getRef(holder.getAdapterPosition()).getKey()).setValue(true);
//
//                        Toast.makeText(getApplicationContext(), model.getGroup_name() + " added to the group", Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        };
//
//        mGroupsList.setLayoutManager(new LinearLayoutManager(this));
//        firebaseRecyclerAdapter.startListening();
//        mGroupsList.setAdapter(firebaseRecyclerAdapter);
//    }


}
