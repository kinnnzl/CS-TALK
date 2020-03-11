package com.example.seapp.ui.additional;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.seapp.LoginActivity;
import com.example.seapp.MainActivity;
import com.example.seapp.R;
import com.example.seapp.ui.announcement.AnnouncementActivity;
import com.example.seapp.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class AdditionalFragment extends Fragment {

    private AdditionalViewModel additionalViewModel;
    private CardView card_History, card_Reply, card_Guide, card_Advertise, card_Contact, card_Logout;
    private ImageView userPic;
    private TextView displayName;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private LinearLayout linearProfile;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private String id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Set title name in actionbar
        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.title_additional));

        additionalViewModel =
                ViewModelProviders.of(this).get(AdditionalViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_additional, container, false);
        userPic = root.findViewById(R.id.userPic);
        displayName = root.findViewById(R.id.displayName);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){

                }
            }
        };


        //Intital User Data
        if (user != null) {
            id = user.getUid();
            final DatabaseReference myRef = database.getReference("User").child(id);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String username = dataSnapshot.child("username").getValue().toString();
                    String userType = dataSnapshot.child("inType").getValue().toString();
                    displayName.setText(username);
                    // if user isn't KMITL People
                    if(userType.equals("บุคลภายนอก")){
                        String pic = dataSnapshot.child("pic").getValue().toString();
                        if(pic.equals("Boy")){
                            userPic.setImageResource(R.drawable.boy);
                        }
                        else{
                            userPic.setImageResource(R.drawable.girl);
                        }
                    }
                    //Admin
                    else if (id.equals("R5cKy3irp6dW14NrZlMNIokx3j43")){
                        linearProfile = root.findViewById(R.id.linear_profile);
                        linearProfile.setVisibility(View.GONE);
                        userPic.setImageResource(R.mipmap.logo);
                    }
                    //KMITL GUYS
                    else {
                        String pic = dataSnapshot.child("pic").getValue().toString();
                        if(pic.equals("Boy")){
                            userPic.setImageResource(R.drawable.boycs);
                        }
                        else{
                            userPic.setImageResource(R.drawable.girlcs);
                        }
                    }



                }//OnDataChange
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }




        // On click cardView history post
        card_History = root.findViewById(R.id.cardHistory);
        card_History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), History.class);
                startActivity(intent);

            }
        });

        // On click cardView reply
        card_Reply = root.findViewById(R.id.cardReply);
        card_Reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // On click cardView guide
        card_Guide = root.findViewById(R.id.cardGuide);
        card_Guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), GuideActivity.class);
                startActivity(intent);
            }
        });

        // On click cardView advertise
        card_Advertise = root.findViewById(R.id.cardAdvertise);
        card_Advertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment,new AnnouncementFragment()).commit();*/
                Intent intent = new Intent(root.getContext(), AnnouncementActivity.class);
                startActivity(intent);
            }
        });

        // On click cardView contact
        card_Contact = root.findViewById(R.id.cardContact);
        card_Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), ContactActivity.class);
                startActivity(intent);
            }
        });

        // On click cardView logout
        card_Logout = root.findViewById(R.id.cardLogout);
        card_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent intent = new Intent(root.getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);


    }

}