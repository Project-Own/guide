package com.example.guide.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Modal.Recomendation;
import com.example.guide.R;
import com.example.guide.adapters.RecomendationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecomendationFragment extends Fragment {
    private RecyclerView recycleView;

    private List<Recomendation> recomendations;


    public RecomendationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_about_recycler, container, false);
        recycleView = v.findViewById(R.id.recycleView);
        recomendations = new ArrayList<>();
        RecomendationAdapter adapter = new RecomendationAdapter(recomendations, getContext(), recycleView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setAdapter(adapter);
        recomendations.add(new Recomendation("NIRAJAN PRJAPATI", "KHCE074BCT024", "prajapatinirajan0@gmail.com", "weqwe", "12321", "sfsdfdsdvsddsfdsf", "nyatapolo"));
        recomendations.add(new Recomendation("NIRJAL PRAJAPATI", "KHCE074BCT026", "nirjalprajapati@gmail.com", "werwe", "445645", "fdgtrtwefefef", "usd "));
        recomendations.add(new Recomendation("ROHIT PRAJAPATI", "KHCE074BCT033", "prajapatinirajan0@gmail.com", "ewr", "786780", "sdfsdfsdfsd", "aud"));
        recomendations.add(new Recomendation("SAHAS PRAJAPATI", "KHCE074BCT037", "sahas_1999@hotmail.com", "gfdfg", "798978", "gdfgdfgdfgdfg", "bara"));


        return v;
    }

}
