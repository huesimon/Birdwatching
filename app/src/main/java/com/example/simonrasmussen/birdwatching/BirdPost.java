package com.example.simonrasmussen.birdwatching;

/**
 * Created by Simon Rasmussen on 08-03-2018.
 */

public class BirdPost {
    private String userWhoPosted;
    private String name;
    private String time;
    private String latitude;
    private String longitude;

    public BirdPost() {
    }

    public BirdPost(String userWhoPosted, String name, String time, String latitude, String longitude) {
        this.userWhoPosted = userWhoPosted;
        this.name = name;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUserWhoPosted() {
        return userWhoPosted;
    }

    public void setUserWhoPosted(String userWhoPosted) {
        this.userWhoPosted = userWhoPosted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
