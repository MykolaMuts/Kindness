package com.project.logic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.project.R;

import java.util.ArrayList;

public class EventAdapter extends ArrayAdapter<Event> {
    private double userLatitude;
    private double userLongitude;
    private Context context;

    public EventAdapter(Context context, ArrayList<Event> events) {
        super(context, 0, events);
        this.context = context;
    }

    public void setUserLocation(double userLatitude, double userLongitude) {
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Event event = getItem(position);

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

            eventDistance.setText("Distance: " + event.getDistanceString(userLatitude, userLongitude));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToEventDetails(event);
                }
            });
        }

        return convertView;
    }

    private void navigateToEventDetails(Event event) {
        NavController navController = Navigation.findNavController((Activity) context, R.id.eventDescription);

        Bundle args = new Bundle();
        args.putSerializable("event", event);

        navController.navigate(R.id.action_eventsFragment_to_eventDetailsFragment, args);
    }
}


