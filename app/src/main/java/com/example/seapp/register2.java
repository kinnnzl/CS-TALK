package com.example.seapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class register2 extends AppCompatActivity {
    Button buttonboy,buttongirl;
    private EditText username;
    private ImageView correct;
    private TextView warning;
    private Button commit;
    private Dialog dlRegis;
    private String fname,lname,email,password,userType,inType;
    public FirebaseDatabase database;
    public DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private String pic = "Boy";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);


        setComponent();
        checkText();

        dlRegis = new Dialog(this);

        buttonboy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonboy.setBackgroundResource(R.drawable.boycspress);
                buttongirl.setBackgroundResource(R.drawable.girlcs);
                pic = "Boy";
            }
        });
        buttongirl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttongirl.setBackgroundResource(R.drawable.girlcspress);
                buttonboy.setBackgroundResource(R.drawable.boycs);
                pic = "Girl";
            }
        });



        commit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                registation();

            }
        });

    } //OnCreate


    public void Popup(View v){

        dlRegis.setContentView(R.layout.activity_register_success);
        commit = dlRegis.findViewById(R.id.cmt2_btn);

        dlRegis.show();

        dlRegis.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class)
                        //to clear all activity in stack
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
        });
    }



    public  void registation(){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String status ="";

                            FirebaseUser users = mAuth.getCurrentUser();
                            User user = new User(username.getText().toString().trim(),fname,lname,userType,inType,pic,status);
                            FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(register2.this,"Successs",Toast.LENGTH_LONG).show();
                                        //to clear all activity in stack
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class)
                                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                                finish();

                                    }
                                    else{
                                        //
                                        Toast.makeText(register2.this,"Failed",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(register2.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }//OnComplete


                });

    }//Regis Method

    public void setComponent(){

        buttonboy=(Button)findViewById(R.id.buttonboycs);
        buttongirl=(Button) findViewById(R.id.buttongirlcs);
        username =(EditText)findViewById(R.id.username);
        correct = (ImageView)findViewById(R.id.correctuser);
        warning =(TextView)findViewById(R.id.warning);
        commit = (Button)findViewById(R.id.cmt2_btn);

        mAuth = FirebaseAuth.getInstance();

        fname = getIntent().getExtras().getString("fname");
        lname = getIntent().getExtras().getString("lname");
        email= getIntent().getExtras().getString("email");
        password = getIntent().getExtras().getString("password");
        userType = getIntent().getExtras().getString("userType");
        inType = getIntent().getExtras().getString("inType");


    }

    public void checkText(){
        username.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isValidFormat(username.getText().toString().trim())&& username.length()>0) {
                    correct.setVisibility(View.VISIBLE);
                    username.setBackgroundResource(R.drawable.borderbox);
                    warning.setVisibility(View.INVISIBLE);
                }
                else  {
                    correct.setVisibility(View.INVISIBLE);
                    warning.setVisibility(View.VISIBLE);
                    username.setBackgroundResource(R.drawable.red_border);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }


    public boolean isValidFormat(final String name) {

        Pattern pattern;
        Matcher matcher;

        final String Name_PATTERN = "^[ก-๙a-zA-Z0-9. ]*$";
        pattern = Pattern.compile(Name_PATTERN);
        matcher = pattern.matcher(name);
        return matcher.matches();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){

        }

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
