package com.example.myapplication.models;

import com.example.myapplication.R;

public class UserModel {
    String name="guest";
    String email="guest@gmail.com";
    String password="123456";
    String profileImg="https://firebasestorage.googleapis.com/v0/b/my-application2-7ef09.appspot.com/o/man.png?alt=media&token=41e1e86f-2b8b-4227-8411-a825b876442d" ;
    String address="NoAddress";
    String phoneNumber="NoPhoneNumber";

    public UserModel() {
    }

    public UserModel(String name, String email, String password, String profileImg, String address, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImg = profileImg;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
