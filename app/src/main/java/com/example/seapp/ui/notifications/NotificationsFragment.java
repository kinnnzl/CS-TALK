package com.example.seapp.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seapp.MainActivity;
import com.example.seapp.R;
import com.example.seapp.ReportAdapter;
import com.example.seapp.ReportNotification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private TextView title;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public FirebaseDatabase database;
    public DatabaseReference mRef;
    //public ArrayList<String> arrayList=new ArrayList<>();
    private RecyclerView recycleView;
    adapterclass mPostAdapter;
    List<CommentNotification> notilist;
    ReportAdapter mReportAdapter;
    List<ReportNotification> reportlist;
    String id,username,detail,postKey;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set title name in actionbar
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_notifications));
        notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        recycleView = (RecyclerView) root.findViewById(R.id.recycleViewnoti);
        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userUid=user.getUid();

      //  mRef = database.getReference().child(userUid).child("Post");.child(userUid).child("Post").child("Comments");
        mRef=database.getReference("User").child(userUid).child("Comments");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){

                }
            }
        };



    //    Intent intent=new Intent(adapterclass.class);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        recycleView.setLayoutManager(layoutManager);
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            id = user.getUid();


            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //check Admin or normal user
        if(user.getUid().equals("R5cKy3irp6dW14NrZlMNIokx3j43")){
            DatabaseReference report_ref = FirebaseDatabase.getInstance().getReference("User").child("R5cKy3irp6dW14NrZlMNIokx3j43")
                    .child("Report");
            report_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    reportlist = new ArrayList<>();

                    for(DataSnapshot reportsnap : dataSnapshot.getChildren()){
                        ReportNotification value = reportsnap.getValue(ReportNotification.class);
                        reportlist.add(value);

                    }

                    mReportAdapter = new ReportAdapter(getActivity(),reportlist);
                    recycleView.setAdapter(mReportAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        else {
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    notilist = new ArrayList<>();

                    for(DataSnapshot notisnap : dataSnapshot.getChildren()){
                        CommentNotification value = notisnap.getValue(CommentNotification.class);
                        notilist.add(value);

                    }

                    mPostAdapter = new adapterclass(getActivity(),notilist);
                    recycleView.setAdapter(mPostAdapter);
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

/*
 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notilist = new ArrayList<>();

                for(DataSnapshot notisnap : dataSnapshot.getChildren()){
                    modelnotification value1 = notisnap.getValue(modelnotification.class);
                    notilist.add(value1);

                }

                mPostAdapter = new adapterclass(getActivity(),notilist);
                recycleView.setAdapter(mPostAdapter);
            }


*/
    }
}


