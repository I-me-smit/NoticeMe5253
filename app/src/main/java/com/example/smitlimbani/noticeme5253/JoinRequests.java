package com.example.smitlimbani.noticeme5253;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class JoinRequests extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private RecyclerView mMembersList;
    private FirebaseRecyclerAdapter<UserDetails, MembersViewHolder> firebaseRecyclerAdapter;
    private String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_requests);

        mMembersList = (RecyclerView) findViewById(R.id.JRrecyclerView);
        mMembersList.setHasFixedSize(true);
        groupId = getIntent().getExtras().getString("group_id");
        final String groupId = getIntent().getExtras().getString("group_id");
        final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        Query keyQuery = databaseReference.child("request_to_join").child(currentUser).child(groupId);

        FirebaseRecyclerOptions<UserDetails> options = new FirebaseRecyclerOptions.Builder<UserDetails>()
                .setIndexedQuery(keyQuery, firebaseDatabase.getReference().child("user_details"), UserDetails.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UserDetails, MembersViewHolder>(options) {
            @NonNull
            @Override
            public MembersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_card, parent, false);
                return new MembersViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final MembersViewHolder holder, int position, @NonNull UserDetails model) {

                holder.mDisplayName.setText(model.getDisplayName());
                holder.mContactNo.setText(model.getContactNo());
                holder.mEmailId.setText(model.getEmailId());

                holder.mMemberCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String uid = getRef(holder.getAdapterPosition()).getKey().toString();
                        databaseReference.child("is_from").child(groupId).child(uid).setValue(true);
                        databaseReference.child("user_groups").child(uid).child(groupId).setValue(true);
//                        databaseReference.child("request_to_join").child(currentUser).child(groupId).orderByChild(uid).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                for (DataSnapshot uid : dataSnapshot.getChildren())
//                                {
//                                    uid.getRef().removeValue();
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
                    }
                });
            }
        };

        mMembersList.setLayoutManager(new LinearLayoutManager(this));
        firebaseRecyclerAdapter.startListening();
        mMembersList.setAdapter(firebaseRecyclerAdapter);
    }
}
