package com.example.seapp;

public class Comment {
    private String postKey;
    private String userid;
    private String detail;
    private int pic;
    private String name;
    private String postTopic;

    public Comment(){}

    public Comment(String userid, String detail,String name, int pic ,String postTopic) {
        this.userid = userid;
        this.detail = detail;
        this.pic = pic;
        this.name = name;
        this.postTopic = postTopic;
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
