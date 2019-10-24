package com.example.guide.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.guide.R;
import com.example.guide.activities.ForexActivity;
import com.example.guide.activities.MapsActivity;
import com.example.guide.activities.PlacesActivity;
import com.example.guide.activities.WeatherActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private Button nav;
    private Button currencyBtn;
    private Button placeBtn;
    private Button weatherBtn;


    public HomeFragment() {
                // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
      //  setupViewPager();

        nav =v.findViewById(R.id.navButton);
        nav.setOnClickListener(v1 -> startActivity(new Intent(getActivity(), MapsActivity.class)));

        currencyBtn = v.findViewById(R.id.currencybutton);
        currencyBtn.setOnClickListener(v12 -> startActivity(new Intent(getActivity(), ForexActivity.class)));
        placeBtn = v.findViewById(R.id.placeButton);
        placeBtn.setOnClickListener(v13 -> startActivity(new Intent(getActivity(), PlacesActivity.class)));
        weatherBtn = v.findViewById(R.id.weatherButton);
        weatherBtn.setOnClickListener(v13 -> startActivity(new Intent(getActivity(), WeatherActivity.class)));


       return  v;
    }



}
