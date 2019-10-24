package com.example.guide.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Modal.Places;
import com.example.guide.R;
import com.example.guide.adapters.PlacesAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlacesActivity extends AppCompatActivity {


    Activity activity;
    Context context;
    private RecyclerView recyclerView;
    private List<Places> placesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        context = this;
        activity = PlacesActivity.this;

        recyclerView = findViewById(R.id.places_recyclerView);
        placesList = new ArrayList<>();
        placesList.add(new Places("Bhaktapur Durbar Square", "logo"));
        placesList.add(new Places("Bhaktapur Durbar Square", "logo"));
        placesList.add(new Places("Bhaktapur Durbar Square", "logo"));

        PlacesAdapter adapter = new PlacesAdapter(placesList, context, activity);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

    }
}
