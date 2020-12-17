package com.mahammadjabi.jbulla.Models;

public class PostsModel
{

    public String uid;
    public String time;
    public String date;
    public String postimage;
    public String username;
    public String description;
    public String profileimage;
    public String postid;

    public PostsModel()
    {

    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public PostsModel(String uid, String time, String date, String postimage, String username, String description, String profileimage, String postid) {
        this.uid = uid;
        this.time = time;
        this.date = date;
        this.postimage = postimage;
        this.username = username;
        this.description = description;
        this.profileimage = profileimage;
        this.postid = postid;
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

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = " "+username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = " "+description;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }
}
