package com.example.smitlimbani.noticeme5253;

import android.support.annotation.NonNull;
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

public class JoinGroup extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference newdbRef = databaseReference;

    private RecyclerView mGrpList;
    private FirebaseRecyclerAdapter<GroupDetails, GroupViewHolder> firebaseRecyclerAdapter;
    private ImageButton mSearch;
    private EditText mSearchGrpBox;
    private String searchQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        mSearch = (ImageButton) findViewById(R.id.searchGrpBtn);

        mSearchGrpBox = (EditText) findViewById(R.id.searchGrpBox);

        mGrpList = (RecyclerView) findViewById(R.id.searchGrpList);
        mGrpList.setHasFixedSize(true);

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchQuery = mSearchGrpBox.getText().toString();
                grpSearch(searchQuery);
            }
        });
    }


    public void grpSearch(final String searchString)
    {
        Query query = databaseReference.child("groups").orderByChild("group_name").startAt(searchString).endAt(searchString + "\uf8ff");

        FirebaseRecyclerOptions<GroupDetails> options = new FirebaseRecyclerOptions.Builder<GroupDetails>()
                .setQuery(query, GroupDetails.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<GroupDetails, GroupViewHolder>(options) {
            @Override
            @NonNull
            protected void onBindViewHolder(@NonNull final GroupViewHolder holder, final int position, @NonNull GroupDetails model) {

                holder.mGroupName.setText(model.getGroup_name().toString());
                holder.mOrgName.setText(model.getOrg_name().toString());
                holder.mGroupsBtn.setText("Join");
                holder.mMembersBtn.setVisibility(View.GONE);
                holder.mGroupsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference.child("groups").child(getRef(holder.getAdapterPosition()).getKey().toString()).child("group_admin").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

                                if(dataSnapshot.getValue().toString().equals(currentUser))
                                {
                                    Toast.makeText(JoinGroup.this, "You are already in the group", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    newdbRef.child("request_to_join").child(dataSnapshot.getValue().toString()).child(getRef(holder.getAdapterPosition()).getKey().toString()).child(currentUser).setValue(true);
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });


            }

            @NonNull
            @Override
            public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_group, parent, false);
                return new GroupViewHolder(view);
            }
        };


        mGrpList.setLayoutManager(new LinearLayoutManager(this));
        firebaseRecyclerAdapter.startListening();
        mGrpList.setAdapter(firebaseRecyclerAdapter);



//


    }
}

