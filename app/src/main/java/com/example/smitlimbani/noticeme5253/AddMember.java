package com.example.smitlimbani.noticeme5253;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AddMember extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private RecyclerView mMembersList;
    private FirebaseRecyclerAdapter<UserDetails, MembersViewHolder> firebaseRecyclerAdapter;
    private ImageButton mSearch;
    private EditText mSearchBox;
    private String searchQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        mSearch = (ImageButton) findViewById(R.id.searchBtn);

        mSearchBox = (EditText) findViewById(R.id.searchBox);

        mMembersList = (RecyclerView) findViewById(R.id.searchMembersList);
        mMembersList.setHasFixedSize(true);

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchQuery = mSearchBox.getText().toString();
                userSearch(searchQuery);
            }
        });

    }

    public void userSearch(String searchString) {
        Query query = databaseReference.child("user_details").orderByChild("displayName").startAt(searchString).endAt(searchString + "\uf8ff");

        FirebaseRecyclerOptions<UserDetails> options = new FirebaseRecyclerOptions.Builder<UserDetails>()
                .setQuery(query, UserDetails.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UserDetails, MembersViewHolder>(options) {
            @NonNull
            @Override
            public MembersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_card, parent, false);
                return new MembersViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final MembersViewHolder holder, final int position, @NonNull final UserDetails model) {

                holder.mDisplayName.setText(model.getDisplayName());
                holder.mContactNo.setText(model.getContactNo());
                holder.mEmailId.setText(model.getEmailId());

                holder.mMemberCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String gid = getIntent().getExtras().getString("gid");
                        String uid = getRef(holder.getAdapterPosition()).getKey();
                        databaseReference.child("is_from").child(gid).child(uid).setValue(true);
                        databaseReference.child("user_groups").child(uid).child(gid).setValue(true);
                        Log.e("enter", "hua");
                        Toast.makeText(getApplicationContext(), model.getDisplayName() + " added to the group", Toast.LENGTH_LONG).show();
                    }
                });
            }
        };

        mMembersList.setLayoutManager(new LinearLayoutManager(this));
        firebaseRecyclerAdapter.startListening();
        mMembersList.setAdapter(firebaseRecyclerAdapter);
    }

}
//    public void searchMembersss(View view)
//    {
//        startActivity(new Intent(Add));
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        firebaseRecyclerAdapter.startListening();
//    }

