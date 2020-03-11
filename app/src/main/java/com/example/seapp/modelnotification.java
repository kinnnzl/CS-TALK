package com.example.seapp.ui.notifications;

public class modelnotification {
    private String userid;
    private String name;
    private String postKey;
    private int pic;
    private String detail;
    private String postTopic;


    public String getPostTopic() {
        return postTopic;
    }

    public void setPostTopic(String postTopic) {
        this.postTopic = postTopic;
    }



    public modelnotification(){

    }
    public modelnotification(String userid, String name, String postKey, int pic, String detail,String postTopic) {
        this.userid = userid;
        this.name = name;
        this.postKey = postKey;
        this.pic = pic;
        this.detail = detail;
        this.postTopic=postTopic;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {return name;}

    public void setName(String name) { this.name = name;}

    public String getPostKey() { return postKey; }

    public void setPostKey(String postKey) { this.postKey = postKey;}

    public int getPic() {return pic;}

    public void setPic(int pic) {this.pic = pic;}

    public String getDetail() {return detail; }

    public void setDetail(String detail) {this.detail = detail;}
}



