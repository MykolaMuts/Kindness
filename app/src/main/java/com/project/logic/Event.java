package com.project.logic;

public class Event {
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