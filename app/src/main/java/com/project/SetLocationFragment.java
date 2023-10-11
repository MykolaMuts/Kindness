package com.project;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class SetLocationFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker selectedMarker;
    private LatLng selectedLatLng;
    private MapView mapView;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set_location, container, false);

        mapView = view.findViewById(R.id.mapViewFull);

        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        mapView.getMapAsync(this);

        Button saveLocationButton = view.findViewById(R.id.saveLocationButton);
        saveLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedLatLng != null) {

                    // Replace this to actually save it to Firebase
                    saveLocation(selectedLatLng);

                    // Navigate back to the previous fragment
                    requireActivity().getSupportFragmentManager().popBackStack();
                } else {
                    Toast.makeText(requireContext(), "Please select a location", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        checkAndSetDefaultLocation();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (selectedLatLng != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLatLng, 15f));
        }

        // add marker
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (selectedMarker != null) {
                    selectedMarker.remove(); // Remove the previous marker
                }

                // Add a new marker at the clicked location
                selectedMarker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Selected Location"));

                selectedLatLng = latLng; // Update selectedLatLng
            }
        });
    }

    private void saveLocation(LatLng location) {
        // Implement logic to save the selected location (e.g., to your event object or Firebase database)
        // Now it just show coordinate
        Toast.makeText(requireContext(), "Location saved: " + location.toString(), Toast.LENGTH_SHORT).show();
    }

    private void checkAndSetDefaultLocation() {
        // Check for GPS permission
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), new OnSuccessListener<android.location.Location>() {
                        @Override
                        public void onSuccess(android.location.Location location) {
                            if (location != null) {
                                selectedLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                            } else {
                                selectedLatLng = new LatLng(52.409538, 16.931992); // Poznan coordinates
                            }

                            if (mMap != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLatLng, 15f));
                            }
                        }
                    });
        } else {
            // Permission not granted, use Poznan as the default location
            selectedLatLng = new LatLng(52.409538, 16.931992);

            if (mMap != null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLatLng, 15f));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mapView != null) {
            mapView.onDestroy();
        }
    }
}
