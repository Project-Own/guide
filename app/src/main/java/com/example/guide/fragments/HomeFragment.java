package com.example.guide.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Modal.Home;
import com.example.guide.R;
import com.example.guide.adapters.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {



    public HomeFragment() {
                // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private List<Home> homeList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
      //  setupViewPager();


        recyclerView = v.findViewById(R.id.homeRecyclerView);
        homeList = new ArrayList<>();
        homeList.add(new Home("Navigation", "navigationws"));
        homeList.add(new Home("Weather", "weather_icon"));
        homeList.add(new Home("Heritages", "nyatapolo_logo"));
        homeList.add(new Home("Food", "okmyanws1"));
        homeList.add(new Home("Calendar", "calendarws1"));
        homeList.add(new Home("Currency", "currencyws2"));

        HomeAdapter adapter = new HomeAdapter(homeList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);


       return  v;
    }



}
