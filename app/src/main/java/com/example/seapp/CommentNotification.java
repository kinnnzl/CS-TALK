package com.example.seapp;

public class CommentNotification {

    private String postKey;
    private String userid;
    private String detail;
    private int pic;
    private String name;
    private String postTopic;

    public CommentNotification(){

    }

    public CommentNotification(String userid, String detail, String name, int pic, String postTopic ) {
        this.userid = userid;
        this.detail = detail;
        this.name = name;
        this.pic = pic;
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

    public String getPostTopic() {
        return postTopic;
    }

    public void setPostTopic(String postTopic) {
        this.postTopic = postTopic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
}
