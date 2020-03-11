package com.example.seapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SearchActivity extends AppCompatActivity {

    private ImageView actionbar_back,iconSearch;
    private TextView searchText,textUnderIcon;
    SearchHistoryAdapter mSearchAdapter;
    private RecyclerView searchRecyclerView;
    private DatabaseReference mDBR;
    List<SearchHistory> searchList,histories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setActionBarTitle();

        // Set button back before activity
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set RecyclerView
        searchRecyclerView = (RecyclerView) findViewById(R.id.SearchRow);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        searchRecyclerView.setLayoutManager(layoutManager);

        mDBR = FirebaseDatabase.getInstance().getReference("Posts");


        textUnderIcon = findViewById(R.id.text_bg_search);
        iconSearch = findViewById(R.id.IconInSearch);



        //search post in firebase
        searchText = findViewById(R.id.etSearch);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textUnderIcon.setVisibility(View.GONE);
                iconSearch.setVisibility(View.GONE);

                showSearchList(searchText.getText().toString());

//                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//                DatabaseReference mRef = firebaseDatabase.getReference("Posts");
//                Query historyQuery = mRef.orderByChild("detail").startAt(searchText.getText().toString())
//                        .endAt(searchText.getText().toString() + "\uf8ff");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

//        layout = (ConstraintLayout) findViewById(R.id.historyLayout);
//        layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference mRef = database.getReference("User").child(mUser.getUid());
//                SearchHistory searchHistory = new Post(mUser.getUid(), searchText.getText().toString(),username,picture);
//                addSearchHistory();
//            }
//        });

    }

    private void showSearchList(final String search_text){
        // Get List SearchPost from database
        mDBR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                searchList = new ArrayList<>();
                for(DataSnapshot searchsnap : dataSnapshot.getChildren()){
                    SearchHistory searchHistory = searchsnap.getValue(SearchHistory.class);

                    if(searchHistory.getDetail().toLowerCase().contains(search_text.toLowerCase())){
                        searchList.add(searchHistory);
                    }
                }

                mSearchAdapter = new SearchHistoryAdapter(getApplicationContext(), searchList);
                searchRecyclerView.setAdapter(mSearchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setActionBarTitle() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.child_searchbar);

        actionbar_back = findViewById(R.id.actionbar_back_search);
        actionbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
//        mDBR = FirebaseDatabase.getInstance().getReference("User").child(mUser.getUid()).child("History");
//        mDBR.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                histories = new ArrayList<>();
//                for(DataSnapshot searchsnap : dataSnapshot.getChildren()){
//                    SearchHistory searchHistory = searchsnap.getValue(SearchHistory.class);
//                    textUnderIcon.setVisibility(View.VISIBLE);
//                    iconSearch.setVisibility(View.VISIBLE);
//                    histories.add(searchHistory);
//
//                }
//
//                mSearchAdapter = new SearchHistoryAdapter(getApplicationContext(), histories);
//                searchRecyclerView.setAdapter(mSearchAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}