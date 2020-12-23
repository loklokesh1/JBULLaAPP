package com.mahammadjabi.jbulla.Models;

public class SharePostsModel
{

    public String date;
    public String description;
    public String postid;
    public String profileimage;

    public String sharedate;
    public String sharepostdescription;
    public String sharepostid;
    public String sharepostimage;
    public String shareprofileimage;
    public String sharetime;
    public String shareusername;
    public String time;
    public String uid;
    public String username;

    public SharePostsModel(String date, String description,
                           String postid, String profileimage,
                           String sharedate, String sharepostdescription,
                           String sharepostid, String sharepostimage,
                           String shareprofileimage, String sharetime,
                           String shareusername, String time, String uid,
                           String username) {
        this.date = date;
        this.description = description;
        this.postid = postid;
        this.profileimage = profileimage;
        this.sharedate = sharedate;
        this.sharepostdescription = sharepostdescription;
        this.sharepostid = sharepostid;
        this.sharepostimage = sharepostimage;
        this.shareprofileimage = shareprofileimage;
        this.sharetime = sharetime;
        this.shareusername = shareusername;
        this.time = time;
        this.uid = uid;
        this.username = username;
    }

    public SharePostsModel()
    {

    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
