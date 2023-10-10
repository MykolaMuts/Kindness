package com.project.logic;

import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;

public class Event implements Serializable {
    private String title;
    private String description;
    private String time;
    private String distance;
    private double latitude;
    private double longitude;

    public Event(String title, String description, String time, String distance) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.distance = distance;
    }

    public Event(String title, String description, String time, String distance, Double latitude, Double longitude) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.distance = distance;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Constructor for Firebase
    public Event(DataSnapshot dataSnapshot) {
        if (dataSnapshot.child("title").exists()) {
            this.title = dataSnapshot.child("title").getValue(String.class);
        }
        if (dataSnapshot.child("description").exists()) {
            this.description = dataSnapshot.child("description").getValue(String.class);
        }
        if (dataSnapshot.child("time").exists()) {
            this.time = dataSnapshot.child("time").getValue(String.class);
        }
        if (dataSnapshot.child("distance").exists()) {
            this.distance = dataSnapshot.child("distance").getValue(String.class);
        }
    }


    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public String getDistance() {
        return distance;
    }
}