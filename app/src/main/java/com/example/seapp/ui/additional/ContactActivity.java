package com.example.seapp.ui.additional;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seapp.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ContactActivity extends AppCompatActivity {

    private ImageView actionbar_back;
    private TextView label_Line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        // Set title name in actionbar
        setActionBarTitle(getString(R.string.title_contact));
        label_Line = findViewById(R.id.labelLine);
        label_Line.setText("@cs.talk");

    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.child_actionbar);
        final TextView titleView = findViewById(R.id.action_bar_title);
        titleView.setText(title);

        actionbar_back = findViewById(R.id.actionbar_back);
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
