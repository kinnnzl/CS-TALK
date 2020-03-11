package com.example.seapp.ui.additional;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.example.seapp.R;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class GuideActivity extends AppCompatActivity {
    private ViewPager screenPager;
    GuideViewPagerAdapter guideViewPagerAdapter;
    TabLayout tabIndicator;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_guide);

        // hide the action bar
        getSupportActionBar().hide();

        // ini views
        tabIndicator = findViewById(R.id.tab_indicator);

        // fill list screen
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem(R.drawable.logo_guide, R.color.colorBackgroundOne, getString(R.string.one_head),
                getString(R.string.one_label), getString(R.string.one_label2)));
        mList.add(new ScreenItem(R.drawable.guide_two, R.color.colorBackgroundOne, getString(R.string.two_head),
                getString(R.string.two_label), null));
        mList.add(new ScreenItem(R.drawable.guide_three, R.color.colorBackgroundOne, getString(R.string.three_head),
                getString(R.string.three_label), null));
        mList.add(new ScreenItem(R.drawable.guide_four, R.color.colorBackgroundOne, getString(R.string.four_head),
                getString(R.string.four_label), null));

        // setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        guideViewPagerAdapter = new GuideViewPagerAdapter(this, mList);
        screenPager.setAdapter(guideViewPagerAdapter);

        // setup tablayout with viewpager
        tabIndicator.setupWithViewPager(screenPager);
        tabIndicator.setSelectedTabIndicatorColor(getColor(R.color.colorBackgroundOne));


        // tablayout add change listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {
                } else {
                    tabIndicator.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

}
