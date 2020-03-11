package com.example.seapp.ui.announcement;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seapp.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Detail3Activity extends AppCompatActivity {

    private ImageView actionbar_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail3);

        // Set title name in actionbar
        setActionBarTitle(getString(R.string.title_announcement));

        //to underline text
        TextView textView;
        textView = findViewById(R.id.textView_detail);
        String htmlString = "<u>รายละเอียด</u>";
        textView.setText(Html.fromHtml(htmlString));
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
