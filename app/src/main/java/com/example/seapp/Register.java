package com.example.seapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Register extends AppCompatActivity  {
    private Button commit;
    private ImageView actionbar_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set actionbar
        setActionBar();

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        FragmentA fragetSupportActionBar().setTitle("Your Activity Title"); // for set actionbar titlegment = new FragmentA();
//        transaction.add(R.id.frame,fragment);
//        transaction.addToBackStack("fragmentA");
//        transaction.commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new KmitlFragment()).commit();

        commit = (Button)findViewById(R.id.cmt2_btn);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), register2.class);
                startActivity(intent);
                //finish();

            }
        });
    }

    private void setActionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.child_actionbar2);
        final TextView titleView = findViewById(R.id.action_bar_title2);
        titleView.setText("ลงทะเบียน");

        actionbar_back = findViewById(R.id.actionbar_back2);
        actionbar_back.setVisibility(View.GONE);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        Fragment fragment;
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectFragment = null;
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();


            switch (menuItem.getItemId()){
                case R.id.KMITL:
                    transaction.setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_rigth);
                    selectFragment = new KmitlFragment();
                    break;
                case R.id.guest:
                    transaction.setCustomAnimations(R.anim.enter_rigth_to_left, R.anim.exit_right_to_left);
                    selectFragment = new GuestFragment();
                    break;
            }//switch
            transaction.replace(R.id.frame,selectFragment).commit();



//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.frame,selectFragment)
                      //.setCustomAnimations(R.anim.enter_rigth_to_left,R.anim.exit_right_to_left,R.anim.enter_left_to_right,R.anim.exit_left_to_rigth)
//                    .commit();

            return  true;
        }//OnNavigation
    };

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
