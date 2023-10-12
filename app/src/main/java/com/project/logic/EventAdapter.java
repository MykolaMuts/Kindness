package com.project.logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.R;

import java.util.ArrayList;

public class EventAdapter extends ArrayAdapter<Event> {
    private double userLatitude;
    private double userLongitude;

    public EventAdapter(Context context, ArrayList<Event> events) {
        super(context, 0, events);
    }

    public void setUserLocation(double userLatitude, double userLongitude) {
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event event = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_event, parent, false);
        }

        TextView eventTitle = convertView.findViewById(R.id.eventTitle);
        TextView eventDescription = convertView.findViewById(R.id.eventDescription);
        TextView eventTime = convertView.findViewById(R.id.eventTime);
        TextView eventDistance = convertView.findViewById(R.id.eventDistance);

        if (event != null) {
            eventTitle.setText(event.getTitle());
            eventDescription.setText(event.getDescription());
            eventTime.setText("Time: " + event.getTime());

            // Calculate and set the distance using user's location
            eventDistance.setText("Distance: " + event.getDistanceString(userLatitude, userLongitude));
        }

        return convertView;
    }
}

