package com.mahammadjabi.jbulla.Models;

public class AskHelpUsersModel {

    public String uid;
    public String time;
    public String asktime;
    public String askamount;


    public AskHelpUsersModel(String uid, String time, String date, String username, String description, String profileimage, String asktime, String askamount) {
        this.uid = uid;
        this.time = time;
        this.date = date;
        this.username = username;
        this.description = description;
        this.profileimage = profileimage;
        this.asktime = asktime;
        this.askamount = askamount;
    }

    public String date;
    public String username;
    public String description;
    public String profileimage;


    public AskHelpUsersModel() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = " "+time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = " "+date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getAsktime() {
        return asktime;
    }

    public void setAsktime(String asktime) {
        this.asktime = asktime;
    }

    public String getAskamount() {
        return askamount;
    }

    public void setAskamount(String askamount) {
        this.askamount = askamount;
    }

}
