package com.example.seapp.ui.additional;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seapp.FriendActivity;
import com.example.seapp.Post;
import com.example.seapp.PostAdapter;
import com.example.seapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class History extends AppCompatActivity {
    private ImageView actionbar_back;
    private TextView label_Line;
    PostAdapter mPostAdapter;
    RecyclerView postRecyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDBR, myPost;
    List<Post> postList;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private FirebaseUser user;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        setActionBarTitle("ประวัติการสร้างกระทู้");
        //set RecyclerView
        postRecyclerView = findViewById(R.id.Post);
        LinearLayoutManager layoutManager = new LinearLayoutManager(History.this);
        //new post will appear on top
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        postRecyclerView.setLayoutManager(layoutManager);

        id = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                }
            }
        };


        firebaseDatabase = FirebaseDatabase.getInstance();
        mDBR = firebaseDatabase.getReference().child("Posts");
        myPost = firebaseDatabase.getReference("User").child(id).child("Post");
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.child_actionbar);
        final TextView titleView = findViewById(R.id.action_bar_title);
        titleView.setText(title);

        actionbar_back = findViewById(R.id.actionbar_back);
        actionbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);
        // Get List Posts from database
        myPost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList = new ArrayList<>();
                //for(DataSnapshot postsnap : dataSnapshot.getChildren()){
                for(DataSnapshot postsnap : dataSnapshot.getChildren()){
                    Post post = postsnap.getValue(Post.class);
                    postList.add(post);
                }

                mPostAdapter = new PostAdapter(History.this,postList);
                postRecyclerView.setAdapter(mPostAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
