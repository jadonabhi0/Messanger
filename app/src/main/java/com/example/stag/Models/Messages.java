package com.example.stag.Models;

public class Messages {

    String uId , message,messageid, imageurl;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    Long time;

    public Messages(String uId, String message, String messageid) {
        this.uId = uId;
        this.message = message;
        this.messageid = messageid;
    }

    public Messages(String uId, String message) {
        this.uId = uId;
        this.message = message;
    }



    public Messages() {
    }


    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


