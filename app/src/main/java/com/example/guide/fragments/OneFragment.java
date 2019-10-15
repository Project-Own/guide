package com.example.guide.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.guide.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment {
    private View view;
    private Button nav;
    public OneFragment() {
                // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_one, container, false);
      //  setupViewPager();
        return view;
    }



}
