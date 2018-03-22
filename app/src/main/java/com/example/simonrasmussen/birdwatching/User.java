package com.example.simonrasmussen.birdwatching;

/**
 * Created by Simon Rasmussen on 08-03-2018.
 */

public class User {
    private String name;
    private String email;
    private String userID;

    public User(String email) {
        this.email = email;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name, String email, String userID) {
        this.name = name;
        this.email = email;

        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}

