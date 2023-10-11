package com.project.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.logic.Event;

public class SaveEvent {
    public static void saveEventToFirebase(Event event, double latitude, double longitude) {
        // Create a Firebase reference
        DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference("events");

        // Generate a unique key for the event
        String eventId = eventsRef.push().getKey();

        // Set latitude and longitude in the event object
        event.setLatitude(latitude);
        event.setLongitude(longitude);

        // Push the event data to Firebase
        eventsRef.child(eventId).setValue(event);
    }
}