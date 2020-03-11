package com.example.seapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
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

public class PostDetials extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private String id,detail,username;
    private int picture;
    private EditText comment_box;
    private ImageView send,actionbar_report,actionbar_back,postOwner_Pic, actionbar_logo, actionbar_additional;
    private TextView actionbar_title,postOwner_Name,postOwner_Detail;
    private TextView commentCount;
    //private ConstraintLayout reportLayout;
    private Button reDelete,cancel;
    CommentAdapter mCommentAdapter;
    int size;
    RecyclerView commentRecyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDBR,userRef;
    List<Comment> commentsList;
    String postID,currentUserName;
    private String ownerID,ownerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detials);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        actionbarManager();





        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){

                }
            }
        };
        intitalData();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                //DatabaseReference mRef = database.getReference("User").child(mUser.getUid());
                Comment comment = new Comment(mUser.getUid(), comment_box.getText().toString().trim(),username,picture,detail);
                CommentNotification commentNotification = new CommentNotification(mUser.getUid(), comment_box.getText().toString().trim(),username,picture,detail);
                addComment(comment,commentNotification);
                comment_box.setText("");

            }
        });

    }




    private void addComment(Comment comment,CommentNotification commentNotification){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef_addComment = firebaseDatabase.getReference("Posts").child(postID).child("Comments").push();
        String key = mRef_addComment.getKey();
        DatabaseReference userPostinUser = firebaseDatabase.getReference("User").child(ownerID).child("Post").child(postID).child("Comments").child(key);
        DatabaseReference owner = firebaseDatabase.getReference().child("Posts").child(postID);

        if(!user.getUid().equals(ownerID)) {
            DatabaseReference postRef = firebaseDatabase.getReference("User").child(ownerID).child("Comments").child(key);
            postRef.setValue(commentNotification);
            commentNotification.setPostKey(key);
        }

        comment.setPostKey(key);
        userPostinUser.setValue(comment);


        mRef_addComment.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(PostDetials.this, "คอมเม้นสำเร็จ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    public void actionbarManager(){
        //setActionbar
        actionbar_report = findViewById(R.id.actionbar_search);
        actionbar_report.setVisibility(View.GONE);
        actionbar_additional = findViewById(R.id.actionbar_additional);
        actionbar_additional.setVisibility(View.VISIBLE);
        actionbar_logo =  findViewById(R.id.actionbar_logo);
        actionbar_logo.setVisibility(View.GONE);
        actionbar_title = (TextView) findViewById(R.id.action_bar_title);
        actionbar_title.setText("CS TALK");
        actionbar_back = findViewById(R.id.actionbar_back2);
        actionbar_back.setVisibility(View.VISIBLE);
        actionbar_back.setImageResource(R.drawable.icon_backactivity);
        postOwner_Pic = (ImageView)findViewById(R.id.postOwner_image);
        postOwner_Name = (TextView)findViewById(R.id.postOwner_username);
        postOwner_Detail = (TextView)findViewById(R.id.postOwner_detail);

        actionbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        actionbar_additional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //reportLayout.setVisibility(View.VISIBLE);
                reDelete.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
            }
        });

    }

    public void intitalData(){

        //setComponent
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        comment_box = (EditText)findViewById(R.id.commet_box);
        send = (ImageView)findViewById(R.id.send);
        commentRecyclerView = findViewById(R.id.comment);
        detail = getIntent().getExtras().getString("Details");
        ownerID = getIntent().getExtras().getString("ownerID");
        int pic = getIntent().getExtras().getInt("Picture");
        ownerName = getIntent().getExtras().getString("Name");
        final String ownerName = getIntent().getExtras().getString("Name");
        postOwner_Detail.setText(detail);
        postOwner_Name.setText(ownerName);
        postOwner_Pic.setImageResource(pic);
        commentCount = (TextView)findViewById(R.id.commentCount);

        //Manage RecycleView set it visible
        LinearLayoutManager layoutManager = new LinearLayoutManager(PostDetials.this);
        commentRecyclerView.setLayoutManager(layoutManager);
        reDelete = (Button)findViewById(R.id.redelete_Btn);

        //Intital User Data
        if (user != null) {
            id = user.getUid();
            final DatabaseReference myRef = database.getReference("User").child(id);
            //myRef.child(id);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                    username = dataSnapshot.child("username").getValue().toString();

                    if(username.equals(ownerName) || id.equals("R5cKy3irp6dW14NrZlMNIokx3j43")){reDelete.setText("ลบกระทู้");
                        reDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference post = database.getReference().child("Posts").child(postID);
                                DatabaseReference postinUser = database.getReference().child("User").child(ownerID).child("Post").child(postID);
                                postinUser.removeValue();
                                post.removeValue();
                                finish();
                            }
                        });
                    }

                    else{reDelete.setText("รายงานกระทู้");
                        reDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(PostDetials.this,Report.class);
                                intent.putExtra("Name",ownerName);
                                intent.putExtra("Detail",detail);
                                intent.putExtra("PostKey",postID);
                                startActivity(intent);
                                reDelete.setVisibility(View.INVISIBLE);
                                cancel.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                    // if user isn't KMITL People
                    String type = dataSnapshot.child("inType").getValue().toString();
                    if (type.equals("บุคลภายนอก")) {
                        String pic = dataSnapshot.child("pic").getValue().toString();
                        if (pic.equals("Boy")) {
                            picture = R.drawable.boy;
                        } else {
                            picture = R.drawable.girl;
                        }
                    }
                    //Admin
                    else if (id.equals("R5cKy3irp6dW14NrZlMNIokx3j43")){
                        picture = R.mipmap.logocrop;
                    }
                    //KMITL GUYS
                    else {
                        String pic = dataSnapshot.child("pic").getValue().toString();
                        if (pic.equals("Boy")) {
                            picture = R.drawable.boycs;
                        } else {
                            picture = R.drawable.girlcs;
                        }
                    }
//                    postOwner_Pic.setImageResource(picture);

                }//OnDataChange

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        postOwner_Pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FriendActivity.class);
                intent.putExtra("FriendID",ownerID = getIntent().getExtras().getString("ownerID"));
                startActivity(intent);
            }
        });

        cancel = (Button)findViewById(R.id.cancel_Btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reDelete.setVisibility(View.INVISIBLE);
                cancel.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);
        // Get List Posts from database
        postID = getIntent().getExtras().getString("PostKey");
        mDBR = database.getReference().child("Posts").child(postID).child("Comments");
        mDBR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentsList = new ArrayList<>();

                for(DataSnapshot postsnap : dataSnapshot.getChildren()){
                    Comment comment = postsnap.getValue(Comment.class);
                    commentsList.add(comment);
                }
                size = commentsList.size();
                commentCount.setText(String.valueOf(size));
                mCommentAdapter = new CommentAdapter(PostDetials.this,commentsList);
                commentRecyclerView.setAdapter(mCommentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
