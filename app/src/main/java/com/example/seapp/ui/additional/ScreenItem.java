package com.example.seapp.ui.additional;

public class ScreenItem {

    int ScreenImg, BackgroundImg;
    String TitleHead, TitleLabel, TitleLabel2;

    public ScreenItem(int screenImg, int backgroundImg, String titleHead, String titleLabel, String titleLabel2) {
        ScreenImg = screenImg;
        BackgroundImg = backgroundImg;
        TitleHead = titleHead;
        TitleLabel = titleLabel;
        TitleLabel2 = titleLabel2;
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

    public void setTitleHead(String titleHead) {
        TitleHead = titleHead;
    }

    public  String getTitleHead() {
        return TitleHead;
    }

    public void setTitleLabel(String titleLabel) {
        TitleLabel = titleLabel;
    }

    public String getTitleLabel() {
        return TitleLabel;
    }

    public void setTitleLabel2(String titleLabel2) {
        TitleLabel2 = titleLabel2;
    }

    public String getTitleLabel2() {
        return TitleLabel2;
    }
}
