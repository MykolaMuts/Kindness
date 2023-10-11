package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.project.databinding.FragmentSecondBinding;
import com.project.firebase.SaveEvent;
import com.project.logic.Event;

public class SecondFragment extends Fragment {

    private double userLatitude;
    private double userLongitude;

    private Event event;

    private FragmentSecondBinding binding;
    private EditText eventTitleEditText;
    private EditText eventDescriptionEditText;
    private EditText eventTimeEditText;

    private Button addButton;

    private Button setLocationButton;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToFirstFragment();
            }
        });

        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEventToFirebase();
            }
        });

        Button locationButton = view.findViewById(R.id.setLocationButton);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToSetLocationFragment();
            }
        });
    }

    private void initializeViews(View view) {
        eventTitleEditText = view.findViewById(R.id.pt_eventTitle);
        eventDescriptionEditText = view.findViewById(R.id.pt_eventDescription);
        eventTimeEditText = view.findViewById(R.id.pl_eventTime);
    }

    private void navigateToFirstFragment() {
        NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment);
    }

    private void navigateToSetLocationFragment() {

        NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_setLocationFragment);
//
//        requireActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.action_SecondFragment_to_setLocationFragment, new SetLocationFragment())
//                .addToBackStack(null)
//                .commit();
    }

    private void addEventToFirebase() {
        // Get the event details from the EditText fields
        String eventTitle = eventTitleEditText.getText().toString();
        String eventDescription = eventDescriptionEditText.getText().toString();
        String eventTime = eventTimeEditText.getText().toString();

        // Check if any of the fields are empty
        if (eventTitle.isEmpty() || eventDescription.isEmpty() || eventTime.isEmpty()) {
            showToast("Please fill in all fields");
            return;
        }

        // Check if location is set
        if (userLatitude == 0.0 || userLongitude == 0.0) {
            showToast("Please set the location before adding the event");
            return;
        }

        // Create a new Event object
        event = new Event(eventTitle, eventDescription, eventTime);

        // Clear the input fields
        clearInputFields();

        SaveEvent.saveEventToFirebase(event, userLatitude, userLongitude);

        showToast("Event added to Firebase");
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void clearInputFields() {
        eventTitleEditText.setText("");
        eventDescriptionEditText.setText("");
        eventTimeEditText.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}



