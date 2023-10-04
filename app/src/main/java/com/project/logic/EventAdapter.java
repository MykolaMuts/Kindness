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
    public EventAdapter(Context context, ArrayList<Event> events) {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        Event event = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_event, parent, false);
        }

        // Lookup view for data population
        TextView eventTitle = convertView.findViewById(R.id.eventTitle);
        TextView eventDescription = convertView.findViewById(R.id.eventDescription);
        TextView eventTime = convertView.findViewById(R.id.eventTime);
        TextView eventDistance = convertView.findViewById(R.id.eventDistance);

        // Populate the data into the template view using the data object
        if (event != null) {
            eventTitle.setText(event.getTitle());
            eventDescription.setText(event.getDescription());
            eventTime.setText("Time: " + event.getTime());
            eventDistance.setText("Distance: " + event.getDistance());
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
