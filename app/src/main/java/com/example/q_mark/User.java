package com.example.q_mark;

public class User {
    private String Name,Email,Pimage,Mobile,uid;

    public User(String name, String email, String pimage, String mobile) {
        Name = name;
        Email = email;
        Pimage = pimage;
        Mobile = mobile;
    }
    public User()
    {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPimage() {
        return Pimage;
    }

    public void setPimage(String pimage) {
        Pimage = pimage;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
