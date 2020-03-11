package com.example.seapp.ui.announcement;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seapp.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AnnouncementActivity extends AppCompatActivity {

    private ImageView actionbar_back,imageView1,imageView2,imageView3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        // Set title name in actionbar
        setActionBarTitle(getString(R.string.title_announcement));

        imageView1 = findViewById(R.id.ivImage1);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AnnouncementActivity.this, Detail1Activity.class));
            }
        });
        imageView2 = findViewById(R.id.ivImage2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AnnouncementActivity.this, Detail2Activity.class));
            }
        });
        imageView3 = findViewById(R.id.ivImage3);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AnnouncementActivity.this, Detail3Activity.class));
            }
        });

    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.child_actionbar2);
        final TextView titleView = findViewById(R.id.action_bar_title2);
        titleView.setText(title);

        actionbar_back = findViewById(R.id.actionbar_back2);
        actionbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
