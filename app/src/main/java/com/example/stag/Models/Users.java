package com.example.stag.Models;

public class Users {

    String username, profilepic, password, email, userid , lastmessage, status;


    public Users(){}

    public Users(String username, String profilepic, String password, String email, String userid, String lastmessage) {
        this.username = username;
        this.profilepic = profilepic;
        this.password = password;
        this.email = email;
        this.userid = userid;
        this.lastmessage = lastmessage;


    }public Users(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;

    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }
}
