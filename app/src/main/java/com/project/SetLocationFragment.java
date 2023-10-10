package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SetLocationFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker selectedMarker;
    private LatLng selectedLatLng;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set_location, container, false);

        Button saveLocationButton = view.findViewById(R.id.saveLocationButton);
        saveLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveLocationButtonClick();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapViewFull);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Set up a default marker at a specific location (e.g., your current location)
        LatLng defaultLocation = new LatLng(0, 0);
        selectedLatLng = defaultLocation; // Initialize with default location

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15f));

        // Set a click listener to add markers
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                onMapClickAction(latLng);
            }
        });
    }

    private void onSaveLocationButtonClick() {
        if (selectedLatLng != null) {
            // Here, you can save the selectedLatLng to your event object or Firebase database
            // Replace this with your actual logic
            Toast.makeText(requireContext(), "Location saved: " + selectedLatLng.toString(), Toast.LENGTH_SHORT).show();

            // Navigate back to the previous fragment
            requireActivity().getSupportFragmentManager().popBackStack();
        } else {
            Toast.makeText(requireContext(), "Please select a location", Toast.LENGTH_SHORT).show();
        }
    }

    private void onMapClickAction(LatLng latLng) {
        if (selectedMarker != null) {
            selectedMarker.remove(); // Remove the previous marker
        }

        // Add a new marker at the clicked location
        selectedMarker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Selected Location"));

        selectedLatLng = latLng; // Update selectedLatLng
    }
}
