package com.project;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.logic.Event;

public class SetLocationFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker selectedMarker;
    private LatLng selectedLatLng;
    private MapView mapView;
    private FusedLocationProviderClient fusedLocationClient;
    private Event event;

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
                    event = new Event(); // Create the Event object
                    event.setLatitude(selectedLatLng.latitude);
                    event.setLongitude(selectedLatLng.longitude);
                    showToast("Location saved");

                    // Pass the updated Event back to the SecondFragment
                    Bundle result = new Bundle();
                    result.putSerializable("event", event);
                    getParentFragmentManager().setFragmentResult("locationResult", result);

                    //requireActivity().getSupportFragmentManager().popBackStack();
                } else {
                    showToast("Please select a location");
                }
            }
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        checkAndSetDefaultLocation();

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (selectedLatLng != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLatLng, 15f));
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (selectedMarker != null) {
                    selectedMarker.remove();
                }

                selectedMarker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Selected Location"));

                selectedLatLng = latLng;
            }
        });
    }

    private void checkAndSetDefaultLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), location -> {
                        if (location != null) {
                            selectedLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                        } else {
                            selectedLatLng = new LatLng(52.409538, 16.931992); // Poznan coordinates
                        }

                        if (mMap != null) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLatLng, 15f));
                        }
                    });
        } else {
            selectedLatLng = new LatLng(52.409538, 16.931992);

            if (mMap != null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLatLng, 15f));
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mapView != null) {
            mapView.onDestroy();
        }
    }
}