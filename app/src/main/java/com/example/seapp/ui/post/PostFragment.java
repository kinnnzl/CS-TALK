package com.example.seapp.ui.post;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.seapp.MainActivity;
import com.example.seapp.Post;
import com.example.seapp.R;
import com.example.seapp.register2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostFragment extends Fragment {

    private PostViewModel postViewModel;
    private LinearLayout layout_post, layout_click_post;
    private EditText edit_Post, edit_post_onClick;
    private TextView count_text,actionbar_text;

    private TextView displayName;
    private ImageView userPic;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private String id,detail,username;
    private int picture;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Set title name in actionbar
        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.title_post));

        postViewModel =
                ViewModelProviders.of(this).get(PostViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_post, container, false);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){

                }
            }
        };

        displayName = (TextView) root.findViewById(R.id.profileName);
        userPic = (ImageView) root.findViewById(R.id.imgProfile);


        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Intital User Data
        if (user != null) {
            id = user.getUid();
            final DatabaseReference myRef = database.getReference("User").child(id);
            //myRef.child(id);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    username = dataSnapshot.child("username").getValue().toString();
                    displayName.setText(username);

                    // if user isn't KMITL People
                    String type = dataSnapshot.child("inType").getValue().toString();
                    if (type.equals("บุคลภายนอก")) {
                        String pic = dataSnapshot.child("pic").getValue().toString();
                        if (pic.equals("Boy")) {
                            userPic.setImageResource(R.drawable.boy);
                            picture = R.drawable.boy;
                        } else {
                            userPic.setImageResource(R.drawable.girl);
                            picture = R.drawable.girl;
                        }
                    }
                    //Admin
                    else if (id.equals("R5cKy3irp6dW14NrZlMNIokx3j43")){
                        userPic.setImageResource(R.mipmap.logocrop);
                        picture = R.mipmap.logocrop;
                    }
                    //KMITL GUYS
                    else {
                        String pic = dataSnapshot.child("pic").getValue().toString();
                        if (pic.equals("Boy")) {
                            userPic.setImageResource(R.drawable.boycs);
                            picture = R.drawable.boycs;

                        } else {
                            userPic.setImageResource(R.drawable.girlcs);
                            picture = R.drawable.girlcs;
                        }
                    }

                }//OnDataChange

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        layout_post = root.findViewById(R.id.layoutPost);
        layout_click_post = root.findViewById(R.id.layoutClickPost);

        edit_Post = root.findViewById(R.id.editPost);
        edit_post_onClick = root.findViewById(R.id.editPostOnClick);

        count_text = root.findViewById(R.id.countText);

        // Set when on focus on count_text
        edit_Post.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                layout_post.setVisibility(View.GONE);
                layout_click_post.setVisibility(View.VISIBLE);

                // Set cursor focus and open keyboard
                edit_post_onClick.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edit_post_onClick, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        // On write post an calculated count text
        edit_post_onClick.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int tmpCount = 150 - s.length();
                count_text.setText(String.valueOf(tmpCount));

                // Call method in MainActivity for set button post on action bar
                ((MainActivity) getActivity())
                        .setActionBarPost(Integer.valueOf(s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        TextView post_text = (TextView)getActivity().findViewById(R.id.actionbar_text);
        post_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create post object
                /*final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                final DatabaseReference mRef = database.getReference("User").child(mUser.getUid());
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Post post = new Post(mUser.getUid(), edit_post_onClick.getText().toString(),
                                dataSnapshot.child("pic").getValue().toString());
                        addPost(post);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/

                // Create post object
                final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference mRef = database.getReference("User").child(mUser.getUid());
                Post post = new Post(mUser.getUid(), edit_post_onClick.getText().toString(),username,picture);
                addPost(post);




            }
        });

        return root;
    }
    //add post to firebase
    private void addPost(Post post) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef_addPost = firebaseDatabase.getReference("Posts").push();


        String key = mRef_addPost.getKey();

        //database.getReference("User").child(id).child("Post").child("PostKey").setValue(key);
        DatabaseReference userPostinUser = firebaseDatabase.getReference("User").child(id).child("Post").child(key);


        post.setPostKey(key);
        userPostinUser.setValue(post);





        mRef_addPost.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(), "สร้างกระทู้สำเร็จ", Toast.LENGTH_SHORT).show();

                // Switch bottom navigation bar and switch fragment to home

                BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
                navView.setSelectedItemId(R.id.navigation_home);

            }
        });
    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);


    }
}