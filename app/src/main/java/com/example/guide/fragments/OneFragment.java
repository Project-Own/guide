package com.example.guide.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.guide.R;
import com.example.guide.activities.MapsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment {
    private Button nav;
    public OneFragment() {
                // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_one, container, false);
      //  setupViewPager();

        nav =v.findViewById(R.id.navButton);
       nav.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getActivity(), MapsActivity.class));
           }
       });
       return  v;
    }



}
