package com.example.project31;

public class ClubUser {
    public String userName;
    public String email;
    public String connectName;

    public String insLink;
    public String phone;
    public String userId;

    public ClubUser(String userName, String email, String connectName, String insLink, String phone, String userId) {
        this.userName = userName;
        this.email = email;
        this.connectName = connectName;
        this.insLink = insLink;
        this.phone = phone;
        this.userId = userId;
    }

    public ClubUser() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConnectName() {
        return connectName;
    }

    public void setConnectName(String connectName) {
        this.connectName = connectName;
    }

    public String getInsLink() {
        return insLink;
    }

    public void setInsLink(String insLink) {
        this.insLink = insLink;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
