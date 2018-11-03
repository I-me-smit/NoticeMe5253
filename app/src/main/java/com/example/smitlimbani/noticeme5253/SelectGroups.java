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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class SelectGroups extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FirebaseRecyclerAdapter<GroupDetails, GroupViewHolder> firebaseRecyclerAdapter;
    private RecyclerView mGroupsList;
    private DatabaseReference user_groupsRef = databaseReference.child("user_groups");
    private DatabaseReference can_post_toRef = databaseReference.child("can_post_to");

    Query keyQuery;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_groups);
        mGroupsList = (RecyclerView)findViewById(R.id.SGgroupsList);
        mGroupsList.setHasFixedSize(true);
        Query query = user_groupsRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        refreshQuery(query);
    }

    public void refreshQuery(Query query) {
        this.keyQuery = query;
        attachRecyclerView();
    }

    private void attachRecyclerView() {

//        Query keyQuery = databaseReference.child("can_post_to").child(groupId);

        FirebaseRecyclerOptions<GroupDetails> options = new FirebaseRecyclerOptions.Builder<GroupDetails>()
                .setIndexedQuery(keyQuery, firebaseDatabase.getReference().child("groups"),GroupDetails.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<GroupDetails, GroupViewHolder>(options) {
            @NonNull
            @Override
            public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_group, parent, false);
                return new GroupViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final GroupViewHolder holder, int position, @NonNull GroupDetails model) {

                holder.mGroupName.setText(model.getGroup_name().toString());
                holder.mOrgName.setText(model.getOrg_name().toString());
                final String currentGroupId = getRef(holder.getAdapterPosition()).getKey().toString();
                holder.mGroupsBtn.setText("Send here");
                holder.mMembersBtn.setText("View Subgroups");
                holder.mGroupsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String noticeId=getIntent().getExtras().getString("noticeId");
                        databaseReference.child("gets_posted").child(noticeId).child(currentGroupId).setValue(true);

                        databaseReference.child("group_sees").child(currentGroupId).child(noticeId).setValue(true);

                        holder.mGroupsBtn.setEnabled(false);
                    }
                });

                holder.mMembersBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Query newQuery = can_post_toRef.child(currentGroupId);
                        refreshQuery(newQuery);
                    }
                });

            }
        };

        mGroupsList.setLayoutManager(new LinearLayoutManager(this));
        firebaseRecyclerAdapter.startListening();
        mGroupsList.setAdapter(firebaseRecyclerAdapter);

    }

}