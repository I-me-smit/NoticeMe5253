package com.example.smitlimbani.noticeme5253;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class JoinOrg extends AppCompatActivity {


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private RecyclerView mOrgList;
    private FirebaseRecyclerAdapter<OrgDetails, OrgViewHolder> firebaseRecyclerAdapter;
    private ImageButton mSearch;
    private EditText mSearchOrgBox;
    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_org);
        mSearch = (ImageButton) findViewById(R.id.searchOrgBtn);

        mSearchOrgBox = (EditText) findViewById(R.id.searchOrgBox);

        mOrgList = (RecyclerView) findViewById(R.id.searchOrgList);
        mOrgList.setHasFixedSize(true);

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchQuery = mSearchOrgBox.getText().toString();
                orgSearch(searchQuery);
            }
        });


    }

        public void orgSearch(String searchString) {
        Query query = databaseReference.child("organizations").orderByChild("org_name").startAt(searchString).endAt(searchString + "\uf8ff");

        FirebaseRecyclerOptions<OrgDetails> options = new FirebaseRecyclerOptions.Builder<OrgDetails>()
                .setQuery(query, OrgDetails.class)
                .build();


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<OrgDetails, OrgViewHolder>(options) {
            @NonNull
            @Override
            public OrgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.org_card, parent, false);
                return new OrgViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final OrgViewHolder holder, final int position, @NonNull final OrgDetails model) {



                holder.mOrgName.setText(model.getOrg_name().toString());
                holder.mOrgType.setText(model.getOrg_type().toString());



                holder.mOrgCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String OrgId = getRef(holder.getAdapterPosition()).getKey().toString();
                        databaseReference.child("user_details").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("org_id").setValue(OrgId);
                       // databaseReference.child("is_from").child(getIntent().getExtras().getString("gid")).child(getRef(holder.getAdapterPosition()).getKey()).setValue(true);

                       Toast.makeText(getApplicationContext(), model.getOrg_name() + " selected", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(JoinOrg.this, OrgUsersType.class).putExtra("org_id",OrgId));
                    }
                });
            }
        };



            mOrgList.setLayoutManager(new LinearLayoutManager(this));
        firebaseRecyclerAdapter.startListening();
        mOrgList.setAdapter(firebaseRecyclerAdapter);



        }
}


