package com.project.logic;

import android.location.Location;

import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;

public class Event implements Serializable {
    private String title;
    private String description;
    private String time;
    private double latitude;
    private double longitude;

    public Event(String title, String description, String time, double latitude, double longitude) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Event(String title, String description, String time) {
        this.title = title;
        this.description = description;
        this.time = time;
    }

    public Event() {
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
//        if (dataSnapshot.child("time").exists()) {
//            this.time = dataSnapshot.child("time").getValue(String.class);
//        }
//        if (dataSnapshot.child("time").exists()) {
//            this.time = dataSnapshot.child("time").getValue(String.class);
//        }
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public float calculateDistance(double userLatitude, double userLongitude) {
        Location userLocation = new Location("");
        userLocation.setLatitude(userLatitude);
        userLocation.setLongitude(userLongitude);

        Location eventLocation = new Location("");
        eventLocation.setLatitude(latitude);
        eventLocation.setLongitude(longitude);

        return userLocation.distanceTo(eventLocation);
    }

    public String getDistanceString(double userLatitude, double userLongitude) {
        float distance = calculateDistance(userLatitude, userLongitude);
        if (distance < 1000) {
            return String.format("%.1f m", distance);
        } else {
            return String.format("%.2f km", distance / 1000);
        }
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
}
