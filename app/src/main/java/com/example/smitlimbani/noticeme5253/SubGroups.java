package com.example.smitlimbani.noticeme5253;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SubGroups extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private RecyclerView mSubGroupList;
    private FirebaseRecyclerAdapter<GroupDetails, GroupViewHolder> firebaseRecyclerAdapter;
    private String groupId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_groups);

        mSubGroupList = (RecyclerView)findViewById(R.id.subGroupsList);
        mSubGroupList.setHasFixedSize(true);
        groupId = getIntent().getExtras().getString("group_id");
        String groupId = getIntent().getExtras().getString("group_id");
        Query keyQuery = databaseReference.child("can_post_to").child(groupId);

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
            protected void onBindViewHolder(@NonNull GroupViewHolder holder, int position, @NonNull GroupDetails model) {

                holder.mGroupName.setText(model.getGroup_name().toString());
                holder.mOrgName.setText(model.getOrg_name().toString());
                final String currentGroupId = getRef(holder.getAdapterPosition()).getKey().toString();
                holder.mGroupsBtn.setText("Add members");
                holder.mMembersBtn.setVisibility(View.GONE);
                holder.mGroupsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(SubGroups.this, AddMember.class).putExtra("gid", currentGroupId));
                    }
                });
            }
        };

        mSubGroupList.setLayoutManager(new LinearLayoutManager(this));
        firebaseRecyclerAdapter.startListening();
        mSubGroupList.setAdapter(firebaseRecyclerAdapter);

    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    public void addSubGroup(View view)
    {
        startActivityForResult(new Intent(SubGroups.this, AddSubGroup.class).putExtra("group_id", groupId),1);
        startActivity(new Intent(SubGroups.this, AddSubGroup.class).putExtra("group_id", groupId));
    }
}
