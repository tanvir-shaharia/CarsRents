package com.example.carsrent;

public class User_Helper {
    String fullName,userName,phone,email,password,user_uid;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public User_Helper(String fullName, String userName, String phone, String email, String password, String user_uid) {
        this.fullName = fullName;
        this.userName = userName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.user_uid = user_uid;
    }

    public User_Helper() {
    }
}
