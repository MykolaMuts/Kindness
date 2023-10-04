package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.project.databinding.FragmentFirstBinding;
import com.project.logic.Event;
import com.project.logic.EventAdapter;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ListView eventListView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventListView = view.findViewById(R.id.eventList);

//        // Create mock data for events
//        ArrayList<String> eventTitles = new ArrayList<>();
//        eventTitles.add("Event 1");
//        eventTitles.add("Event 2");
//        eventTitles.add("Event 3");

        // Create mock event data
        ArrayList<Event> events = new ArrayList<>();
        events.add(new Event("Lost my cat", "Lost a cat yesterday, it's black with white paws and a collar", "20 minutes ago", "2 km"));

        // Create an ArrayAdapter to display the event data in the ListView
//        ArrayAdapter<Event> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, events);
        EventAdapter adapter = new EventAdapter(requireContext(), events);
        eventListView.setAdapter(adapter);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
