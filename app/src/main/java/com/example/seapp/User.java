package com.example.seapp;

public class User {
    public String username;
    public String fname;
    public String lname;
    public String userType; // ใน หรือ นอกสถาบัน
    public String inType; //นศ / บุคลากร
    public String pic;
    public String status;

    public User(String username,String fname,String lname,String userType,String inType,String pic,String status){
        this.username = username;
        this.fname = fname;
        this.lname =  lname;
        this.userType = userType;
        this.inType = inType;
        this.pic = pic;
        this.status= status;

    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setInType(String inType) {
        this.inType = inType;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUsername() {
        return username;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getUserType() {
        return userType;
    }

    public String getInType() {
        return inType;
    }

    public String getPic() {
        return pic;
    }

    public String getStatus() {
        return status;
    }
}
