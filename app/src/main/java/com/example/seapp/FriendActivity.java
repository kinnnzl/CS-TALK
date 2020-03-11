package com.example.seapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FriendActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private TextView friendName,header;
    private ImageView friendPic, iconClipboard;
    private EditText status,id,type;
    private String friendID;
    PostAdapter mPostAdapter;
    RecyclerView postRecyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDBR,friendPost;
    List<Post> postList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setTitle("ค้นหาเพื่อน");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.child_actionbar);
        ImageView back =(ImageView) findViewById(R.id.actionbar_back);
        final TextView titleView = findViewById(R.id.action_bar_title);
        friendName = (TextView) findViewById(R.id.myfriendName);
        friendPic = (ImageView) findViewById(R.id.myfriendPic);
        header = (TextView)findViewById(R.id.Header);
        status = (EditText)findViewById(R.id.status);
        id = (EditText)findViewById(R.id.Id);
        type = (EditText)findViewById(R.id.Type);
        titleView.setText("โปรไฟล์เพื่อน");
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        friendID = getIntent().getExtras().getString("FriendID");
        DatabaseReference myRef = database.getReference("User").child(friendID);
        //set RecyclerView
        postRecyclerView = findViewById(R.id.Post);
        LinearLayoutManager layoutManager = new LinearLayoutManager(FriendActivity.this);
        //new post will appear on top
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        postRecyclerView.setLayoutManager(layoutManager);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDBR = firebaseDatabase.getReference().child("Posts");
        friendPost = firebaseDatabase.getReference("User").child(friendID).child("Post");




        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                header.setText("กระทู้  "+dataSnapshot.child("username").getValue().toString());
                status.setText(dataSnapshot.child("status").getValue().toString());
                friendName.setText(dataSnapshot.child("username").getValue().toString());
                id.setText(friendID);
                type.setText(dataSnapshot.child("inType").getValue().toString());
                if(type.equals("บุคลากรภายนอก")){
                    String pic = dataSnapshot.child("pic").getValue().toString();
                    if(pic.equals("Boy")){
                        friendPic.setImageResource(R.drawable.boy);
                    }
                    else{
                        friendPic.setImageResource(R.drawable.girl);
                    }
                }
                //Admin
                else if (user.getUid().equals("R5cKy3irp6dW14NrZlMNIokx3j43")){
                    friendPic.setImageResource(R.mipmap.logocrop);
                }
                else{
                    String pic = dataSnapshot.child("pic").getValue().toString();
                    if(pic.equals("Boy")){
                        friendPic.setImageResource(R.drawable.boycs);
                    }
                    else{
                        friendPic.setImageResource(R.drawable.girlcs);
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSupportNavigateUp();
            }
        });

        iconClipboard = findViewById(R.id.icon_clipboard);
        iconClipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionCopyClipboard();
            }
        });

    }

    private void actionCopyClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text", getIntent().getExtras().getString("FriendID"));
        clipboard.setPrimaryClip(clip);
        Toast.makeText(FriendActivity.this, "คัดลอกสำเร็จ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);
        // Get List Posts from database
        friendPost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList = new ArrayList<>();
                //for(DataSnapshot postsnap : dataSnapshot.getChildren()){
                for(DataSnapshot postsnap : dataSnapshot.getChildren()){
                    Post post = postsnap.getValue(Post.class);
                    postList.add(post);
                }

                mPostAdapter = new PostAdapter(FriendActivity.this,postList);
                postRecyclerView.setAdapter(mPostAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // Button back before activity
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
