package com.example.seapp;

public class SearchHistory {
    private String postKey;
    private String userid;
    private String detail;
    private int pic;
    private String name;

    public SearchHistory(){

    }

    public SearchHistory(String userid, String detail, String name, int pic) {
        this.userid = userid;
        this.detail = detail;
        this.name = name;
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

}
