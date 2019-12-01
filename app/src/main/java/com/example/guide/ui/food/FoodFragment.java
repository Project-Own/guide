package com.example.guide.ui.food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Model.Places;
import com.example.guide.R;
import com.example.guide.adapters.FoodAdapter;
import com.example.guide.adapters.PlacesAdapter;

import java.util.List;

public class FoodFragment extends Fragment {

    private FoodViewModel mViewModel;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private TextView titleText;
    public static FoodFragment newInstance() {
        return new FoodFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.places_fragment, container, false);
        recyclerView = view.findViewById(R.id.places_recyclerView);
        titleText = view.findViewById(R.id.titleText);
        titleText.setText("Food");
        mLayoutManager = new LinearLayoutManager(getContext());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final NavController navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);

        mViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        mViewModel.loadFood().observe(this, new Observer<List<Places>>() {
            @Override
            public void onChanged(List<Places> places) {
                PlacesAdapter adapter = new PlacesAdapter(places, recyclerView, getContext(), navController);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);

            }
        });
    }


}
