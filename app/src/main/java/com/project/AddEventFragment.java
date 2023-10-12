package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;

import com.project.databinding.AddEventBinding;
import com.project.firebase.SaveEvent;
import com.project.logic.Event;

public class AddEventFragment extends Fragment {

    private double eventLatitude;
    private double eventLongitude;

    private Event event;

    private AddEventBinding binding;
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
        binding = AddEventBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("locationResult", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if (requestKey.equals("locationResult")) {
                    event = (Event) result.getSerializable("event");
                    eventLatitude = event.getLatitude();
                    eventLongitude = event.getLongitude();

                }
            }
        });
    }

    private void initializeViews(View view) {
        eventTitleEditText = view.findViewById(R.id.pt_eventTitle);
        eventDescriptionEditText = view.findViewById(R.id.pt_eventDescription);
        eventTimeEditText = view.findViewById(R.id.pl_eventTime);
    }

    private void navigateToFirstFragment() {
        NavHostFragment.findNavController(AddEventFragment.this)
                .navigate(R.id.action_addEventFragment_to_eventsFragment);
    }

    private void navigateToSetLocationFragment() {

        NavHostFragment.findNavController(AddEventFragment.this)
                .navigate(R.id.action_addEventFragment_to_setLocationFragment);
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

        if (eventTitle.isEmpty() || eventDescription.isEmpty() || eventTime.isEmpty()) {
            showToast("Please fill in all fields");
            return;
        }

        if (eventLatitude == 0.0 || eventLongitude == 0.0) {
            showToast("Please set the location before adding the event");
            return;
        }

        event = new Event(eventTitle, eventDescription, eventTime);

        // Clear the input fields
        clearInputFields();

        SaveEvent.saveEventToFirebase(event, eventLatitude, eventLongitude);

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