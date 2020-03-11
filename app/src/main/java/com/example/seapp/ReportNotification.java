package com.example.seapp;

public class ReportNotification {
    private String postKey;
    private String postTopic;
    private String username;

    public ReportNotification() {

    }
    public ReportNotification(String postKey, String postTopic,String username) {
        this.postKey = postKey;
        this.postTopic = postTopic;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPostTopic() {
        return postTopic;
    }

    public void setPostTopic(String postTopic) {
        this.postTopic = postTopic;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }
}
