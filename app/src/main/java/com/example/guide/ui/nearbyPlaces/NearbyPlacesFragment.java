package com.example.guide.ui.nearbyPlaces;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Model.NearbySearch.Result;
import com.example.guide.R;
import com.example.guide.adapters.NearbyAdapter;
import com.example.guide.databinding.NearbyPlacesFragmentBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class NearbyPlacesFragment extends Fragment {

    private Spinner convertPlacesSpinner;
    private NearbyPlacesViewModel mViewModel;
    private FusedLocationProviderClient fusedLocationClient;
    private String userLocation;
    private RecyclerView recyclerView;
    private NearbyPlacesFragmentBinding binding;

    public static NearbyPlacesFragment newInstance() {
        return new NearbyPlacesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.bind(inflater.inflate(R.layout.nearby_places_fragment, container, false));

        View view = binding.getRoot();
        recyclerView = view.findViewById(R.id.recyclerViewNearby);
        convertPlacesSpinner = view.findViewById(R.id.spinnerNearby);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NearbyPlacesViewModel.class);

        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            String longitude = Double.toString(location.getLongitude());
                            String latitude = Double.toString(location.getLatitude());
                            userLocation = latitude+","+longitude;
                            mViewModel.setUserLocation(userLocation);
                            mViewModel.loadResult().observe(getActivity(), new Observer<List<Result>>() {
                                @Override
                                public void onChanged(List<Result> results) {
                                    NearbyAdapter nearbyAdapter = new NearbyAdapter(results, recyclerView, getContext());
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                    recyclerView.setLayoutManager(mLayoutManager);
                                    recyclerView.setAdapter(nearbyAdapter);
                                    recyclerView.hasFixedSize();
                                }
                            });


                            mViewModel.loadSpinnerData().observe(getActivity(), new Observer<String[]>() {
                                @Override
                                public void onChanged(String[] s) {

                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, s);
                                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                    convertPlacesSpinner.setAdapter(arrayAdapter);

                                }
                            });

                            Log.i("Nearby", userLocation);
                        }
                    }
                });


          }


}
