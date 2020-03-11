package com.example.seapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.seapp.ui.additional.AdditionalFragment;
import com.example.seapp.ui.profile.ProfileFragment;
import com.example.seapp.ui.home.HomeFragment;
import com.example.seapp.ui.notifications.NotificationsFragment;
import com.example.seapp.ui.post.PostFragment;
import com.google.firebase.auth.FirebaseAuth;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ImageView actionbar_search;
    private TextView actionbar_text;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        navView.setOnNavigationItemSelectedListener(navListener);

        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_notifications, R.id.navigation_post, R.id.navigation_profile, R.id.navigation_additional)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        final TextView titleView = findViewById(R.id.action_bar_title);
        titleView.setText(title);

        actionbar_search = findViewById(R.id.actionbar_search);
        actionbar_text = findViewById(R.id.actionbar_text);

        switch (title) {
            case "เพิ่มเติม":
                actionbar_search.setImageResource(R.drawable.icon_search_friend);
                actionbar_search.setVisibility(View.VISIBLE);
                actionbar_text.setVisibility(View.GONE);
                actionbar_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, SearchFriend.class);
                        startActivity(intent);
                    }
                });
                break;
            case "สร้างกระทู้คำถาม":
                actionbar_text.setText("Post");
                actionbar_search.setVisibility(View.GONE);
                actionbar_text.setVisibility(View.INVISIBLE);
                break;
            case "หน้าหลัก":
                actionbar_search = findViewById(R.id.actionbar_search);
                actionbar_search.setImageResource(R.drawable.icon_search);
                actionbar_search.setVisibility(View.VISIBLE);
                actionbar_text.setVisibility(View.GONE);
                actionbar_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(intent);
                    }
                });
                break;
            default:
                actionbar_search = findViewById(R.id.actionbar_search);
                actionbar_search.setVisibility(View.INVISIBLE);
                break;
        }

    }

    public void setActionBarPost(int countText) {
        if (countText > 5) {
            actionbar_text.setVisibility(View.VISIBLE);
//            actionbar_text.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {


//                    // Switch bottom navigation bar and switch fragment to home
 //                   BottomNavigationView navView = findViewById(R.id.nav_view);
//                    navView.setSelectedItemId(R.id.navigation_home);
//
//                }
//            });
        } else {
            actionbar_text.setVisibility(View.INVISIBLE);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.navigation_notifications:
                            selectedFragment = new NotificationsFragment();
                            break;
                        case R.id.navigation_post:
                            selectedFragment = new PostFragment();
                            break;
                        case R.id.navigation_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                        case R.id.navigation_additional:
                            selectedFragment = new AdditionalFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                            selectedFragment).commit();

                    return true;
                }
            };

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
