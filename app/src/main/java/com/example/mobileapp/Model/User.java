package com.example.mobileapp.Model;

public class User implements IUser {
    private String user,password;

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public User() {

    }
    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
