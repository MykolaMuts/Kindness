package com.project.logic;

import java.io.Serializable;

public class Event implements Serializable {
    private String title;
    private String description;
    private String time;
    private String distance;

    public Event(String title, String description, String time, String distance) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.distance = distance;
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