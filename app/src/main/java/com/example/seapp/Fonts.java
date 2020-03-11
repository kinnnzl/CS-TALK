package com.example.seapp;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Fonts extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initFont();
    }

    private void initFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/FC Lamoon Regular ver 1.00.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
