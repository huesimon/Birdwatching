package com.example.simonrasmussen.birdwatching.Objects;

/**
 * Created by Simon Rasmussen on 12-04-2018.
 */

//API URL http://birdobservationservice.azurewebsites.net/Service1.svc/birds

public class Bird {
    private String created;
    private int ID;
    private String nameDanish;
    private String nameEnglish;
    private String photoURL;


    public Bird(String created, int ID, String nameDanish, String nameEnglish, String photoURL) {
        this.created = created;
        this.ID = ID;
        this.nameDanish = nameDanish;
        this.nameEnglish = nameEnglish;
        this.photoURL = photoURL;
    }

    public Bird() {
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNameDanish() {
        return nameDanish;
    }

    public void setNameDanish(String nameDanish) {
        this.nameDanish = nameDanish;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}