package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.logic.Event;

public class EventDetailsFragment extends Fragment implements OnMapReadyCallback {

    private Event event;

    public EventDetailsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_details, container, false);

        // Get the event details from the arguments
        Bundle args = getArguments();
        if (args != null) {
            event = (Event) args.getSerializable("event");
        }

        TextView eventTitleTextView = view.findViewById(R.id.eventTitleTextView);
        TextView eventDescriptionTextView = view.findViewById(R.id.eventDescriptionTextView);
        TextView eventTimeTextView = view.findViewById(R.id.eventTimeTextView);

        eventTitleTextView.setText(event.getTitle());
        eventDescriptionTextView.setText(event.getDescription());
        eventTimeTextView.setText(event.getTime());

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        double eventLat = event.getLatitude();
        double eventLng = event.getLongitude();

        LatLng eventLocation = new LatLng(eventLat, eventLng);

        googleMap.addMarker(new MarkerOptions().position(eventLocation).title(event.getTitle()));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLocation, 15f));
    }
}
