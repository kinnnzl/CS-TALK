package com.example.seapp;

public class ScreenItem {

    int ScreenImg, BackgroundImg;

    public ScreenItem(int screenImg, int backgroundImg) {
        ScreenImg = screenImg;
        BackgroundImg = backgroundImg;
    }

    public void setScreenImg(int screenImg) {
        ScreenImg = screenImg;
    }

    public int getScreenImg() {
        return ScreenImg;
    }

    public void setBackgroundImg(int backgroundImg) {
        BackgroundImg = backgroundImg;
    }

    public int getBackgroundImg() {
        return BackgroundImg;
    }
}
