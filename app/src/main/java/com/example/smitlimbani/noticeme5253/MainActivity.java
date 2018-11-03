package com.example.smitlimbani.noticeme5253;

import android.content.ContentProvider;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.annotation.GlideType;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URI;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private RecyclerView mDashboard;

    private FirebaseRecyclerAdapter<NoticeDetails, NoticeViewHolder> firebaseRecyclerAdapter;
    private FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();
    private StorageReference storageReference = firebaseStorage.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        setContentView(R.layout.content_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNotice.class));
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });


        mDashboard = findViewById(R.id.MAdashboard);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDashboard.setHasFixedSize(true);


//        databaseReference.child("user_groups").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
//                {
//                    Query query = databaseReference.child("group_sees").child(dataSnapshot1.getKey().toString());
//
//
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


        FirebaseRecyclerOptions<NoticeDetails> options = new FirebaseRecyclerOptions.Builder<NoticeDetails>()
                .setQuery(databaseReference.child("notice"), NoticeDetails.class).build();



        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<NoticeDetails, NoticeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final NoticeViewHolder holder, int position, @NonNull NoticeDetails model) {
                holder.mNoticeTitle.setText(model.getNotice_title());
                holder.mExpiryTime.setText(model.getNotice_expiry());
                holder.mNoticeContent.setText(model.getNotice_content());

                String nId =getRef(holder.getAdapterPosition()).getKey().toString();
                String imgLocation = "notices/"+nId;

                StorageReference pathRef = storageReference.child(imgLocation);

                pathRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Glide.with(MainActivity.this)
                                .load(uri)
                                .into(holder.mNoticeImage);
                    }
                });



            }

            @NonNull
            @Override
            public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_card, parent, false);
                return new NoticeViewHolder(view);

            }
        };

        mDashboard.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        firebaseRecyclerAdapter.startListening();

        mDashboard.setAdapter(firebaseRecyclerAdapter);





//        DatabaseReference dbr1 = databaseReference;
//
//        dbr1.child("user_groups").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot ds: dataSnapshot.getChildren())
//                {
//                    if(ds.getValue().equals(true))
//                    {
//                        DatabaseReference dbr2 = databaseReference;
//                        dbr2.child("gets_posted").orderByValue()
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(MainActivity.this, JoinOrg.class));
        } else if (id == R.id.nav_gallery) {

            startActivity(new Intent(MainActivity.this, JoinGroup.class));
        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(MainActivity.this, AddNotice.class));
        } else if (id == R.id.nav_manage) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else if (id == R.id.nav_share) {
            startActivity(new Intent(MainActivity.this, CreateOrg.class));
        } else if (id == R.id.nav_send) {

            startActivity(new Intent(MainActivity.this, ManageGroups.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
