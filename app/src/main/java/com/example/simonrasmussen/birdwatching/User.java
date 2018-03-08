package com.example.simonrasmussen.birdwatching;

import java.io.Serializable;

/**
 * Created by Simon Rasmussen on 08-03-2018.
 */

public class User implements Serializable {
    private String email;
    private String userID;

    public User() {
    }

    public User(String email, String userID) {
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

