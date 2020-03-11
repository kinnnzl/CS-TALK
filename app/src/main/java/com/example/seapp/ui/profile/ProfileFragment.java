package com.example.seapp.ui.profile;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.seapp.MainActivity;
import com.example.seapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileFragment extends Fragment  {

    private ProfileViewModel profileViewModel;
    private Button commit,edit;
    private TextView nameTxt,displayName;
    private TextView nameLength;
    private TextView statusLength;
    private EditText name,idText,type;
    private EditText status, userType;
    private ImageView userPic, iconClipboard;
    private LinearLayout linearStatus, linearStatusEdit;
    final int maxlength = 20;
    private  int lengthName;
    private  int remainNameLength;
    private  int lengthStatus;
    private  int remainStatusLength;
    private  TextView expression;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private String id;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {


        // Set title name in actionbar
        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.title_profile));

        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_profile, container, false);

        setComponent(root);

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
            //myRef.child(id);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String username = dataSnapshot.child("username").getValue().toString();
                    displayName.setText(username);
                    name.setText(dataSnapshot.child("username").getValue().toString());
                    status.setText(dataSnapshot.child("status").getValue().toString());
                    String usern;
                    usern = mAuth.getInstance().getCurrentUser().getUid();
                    idText.setText(usern);
                    userType.setText(dataSnapshot.child("inType").getValue().toString());
                    status.setText(dataSnapshot.child("status").getValue().toString());

                    // if user isn't KMITL People
                    if((userType.getText().toString().trim()).equals("บุคลภายนอก")){
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
                        linearStatus = root.findViewById(R.id.linear_status);
                        linearStatus.setVisibility(View.GONE);
                        linearStatusEdit = root.findViewById(R.id.linear_status_edit);
                        linearStatusEdit.setVisibility(View.GONE);
                        edit.setVisibility(View.GONE);
                        userPic.setImageResource(R.mipmap.logocrop);
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

        iconClipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionCopyClipboard();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            actionAfterClickEditButton();
            }
        });

        //Counting Text Remain
        realtimeCountText();

        // Click Commit Buttton
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commitAction();

            }
        });

        return root;
    }

    private void actionCopyClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text", idText.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getActivity(), "คัดลอกสำเร็จ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);


    }

    //After Click commit
    public void commitAction(){
        if (isValidFormat(name.getText().toString().trim())) {
            name.setBackgroundResource(R.drawable.edittext_grey);
            status.setBackgroundResource(R.drawable.edittext_grey);
            nameTxt.setText("ชื่อผู้ใช้");
            name.setFocusable(false);
            name.setFocusableInTouchMode(false);
            status.setFocusable(false);
            status.setFocusableInTouchMode(false);
            Toast.makeText(getActivity(), "แก้ไขเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();
            commit.setVisibility(View.INVISIBLE);
            nameLength.setVisibility(View.INVISIBLE);
            statusLength.setVisibility(View.INVISIBLE);
            expression.setVisibility(View.INVISIBLE);
            if (!TextUtils.isEmpty(name.getText().toString().trim())) {
                FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("username").setValue(name.getText().toString().trim());
            }
            if (!TextUtils.isEmpty(status.getText().toString().trim())) {
                FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("status").setValue(status.getText().toString().trim());
            }
        } else {
            name.setBackgroundResource(R.drawable.red_border);
            expression.setVisibility(View.VISIBLE);

        }

    }

    public void setComponent(View root){
        iconClipboard = root.findViewById(R.id.icon_clipboard);
        edit = root.findViewById(R.id.edit_btn);
        nameTxt =(TextView)root.findViewById(R.id.nameText);
        name =(EditText)root.findViewById(R.id.nameEdit);
        status =(EditText)root.findViewById(R.id.statusEdit);
        nameLength=(TextView)root.findViewById(R.id.nameLength);
        statusLength=(TextView)root.findViewById(R.id.statusLength);
        commit = (Button)root.findViewById(R.id.commit_btn);
        expression =(TextView)root.findViewById(R.id.Expression_text);
        displayName=(TextView) root.findViewById(R.id.displayName);
        idText = (EditText)root.findViewById(R.id.idText);
        userType = (EditText)root.findViewById(R.id.userType);
        userPic = (ImageView)root.findViewById(R.id.userPic);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

    }




    public void actionAfterClickEditButton(){
        name.setBackgroundResource(R.drawable.border);
        status.setBackgroundResource(R.drawable.border);
        nameTxt.setText("ชื่อผู้ใช้ (ห้ามตั้งชื่อที่เข้าข่ายลามกอนาจาร)");
        name.setFocusable(true);
        name.setFocusableInTouchMode(true);
        status.setFocusable(true);
        status.setFocusableInTouchMode(true);
        commit.setVisibility(View.VISIBLE);
        nameLength.setVisibility(View.VISIBLE);
        statusLength.setVisibility(View.VISIBLE);

        //Intitial Count Text
        lengthName = name.length();
        remainNameLength = maxlength- lengthName;
        String name_Remain = String.valueOf(remainNameLength);
        nameLength.setText(name_Remain);

        lengthStatus = status.length();
        remainStatusLength = maxlength - lengthStatus;
        String status_Remain = String.valueOf(remainStatusLength);
        statusLength.setText(status_Remain);
    }



    //Check name format
    public boolean isValidFormat(final String name) {

        Pattern pattern;
        Matcher matcher;

        final String Name_PATTERN = "^[ก-๙a-zA-Z0-9. ]*$";
        pattern = Pattern.compile(Name_PATTERN);
        matcher = pattern.matcher(name);
        return matcher.matches();

    }

    //Count Text Remain
    public void realtimeCountText(){

        //Realtime Count Status Text
        status.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                lengthStatus = status.length();
                remainStatusLength = maxlength - lengthStatus;
                String status_Remain = String.valueOf(remainStatusLength);
                statusLength.setText(status_Remain);
            }
        });



        //Realtime Count Name Text
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                lengthName = name.length();
                remainNameLength = maxlength- lengthName;
                String convert = String.valueOf(remainNameLength);
                nameLength.setText(convert);
            }
        });

    }//End Of Realtime Method


}