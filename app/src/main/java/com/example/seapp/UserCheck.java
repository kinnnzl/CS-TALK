package com.example.seapp;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserCheck extends Application {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    /*to use this class
    add this : android:name=".UserCheck"
    in AndroidManifest.xml
     */

    @Override
    public void onCreate() {
        super.onCreate();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

            //can't use this because it can't finish() LoginActivity
        }
    }
}
