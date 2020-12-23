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

    public String sharedate;
    public String sharepostdescription;
    public String sharepostid;
    public String sharepostimage;
    public String shareprofileimage;
    public String sharetime;
    public String shareusername;

    public PostsModel()
    {

    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getSharedate() {
        return sharedate;
    }

    public void setSharedate(String sharedate) {
        this.sharedate = sharedate;
    }

    public String getSharepostdescription() {
        return sharepostdescription;
    }

    public void setSharepostdescription(String sharepostdescription) {
        this.sharepostdescription = sharepostdescription;
    }

    public String getSharepostid() {
        return sharepostid;
    }

    public void setSharepostid(String sharepostid) {
        this.sharepostid = sharepostid;
    }

    public String getSharepostimage() {
        return sharepostimage;
    }

    public void setSharepostimage(String sharepostimage) {
        this.sharepostimage = sharepostimage;
    }

    public String getShareprofileimage() {
        return shareprofileimage;
    }

    public void setShareprofileimage(String shareprofileimage) {
        this.shareprofileimage = shareprofileimage;
    }

    public String getSharetime() {
        return sharetime;
    }

    public void setSharetime(String sharetime) {
        this.sharetime = sharetime;
    }

    public String getShareusername() {
        return shareusername;
    }

    public void setShareusername(String shareusername) {
        this.shareusername = shareusername;
    }

    public PostsModel(String uid,
                      String time, String date,
                      String postimage, String username,
                      String description, String profileimage,
                      String postid,
                      String sharedate, String sharepostdescription,
                      String sharepostid, String sharepostimage,
                      String shareprofileimage, String sharetime,
                      String shareusername

                        ) {
        this.uid = uid;
        this.time = time;
        this.date = date;
        this.postimage = postimage;
        this.username = username;
        this.description = description;
        this.profileimage = profileimage;
        this.postid = postid;

        this.sharedate = sharedate;
        this.sharepostdescription = sharepostdescription;
        this.sharepostid = sharepostid;
        this.sharepostimage = sharepostimage;
        this.shareprofileimage = shareprofileimage;
        this.sharetime = sharetime;
        this.shareusername = shareusername;
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
