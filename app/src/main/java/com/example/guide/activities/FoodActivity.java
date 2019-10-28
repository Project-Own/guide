package com.example.guide.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Modal.Food;
import com.example.guide.R;
import com.example.guide.adapters.FoodAdapter;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {


    Activity activity;
    Context context;
    private RecyclerView recyclerView;
    private List<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        context = this;
        activity = FoodActivity.this;

        recyclerView = findViewById(R.id.places_recyclerView);
        foodList = new ArrayList<>();
        foodList.add(new Food("Newari Cuisine", " ", "nyatapolo"));
        foodList.add(new Food("Chatamari", "", "nirajan"));
        foodList.add(new Food("Mari", "", "pressure"));

        FoodAdapter adapter = new FoodAdapter(foodList, recyclerView, context, activity);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

    }
}
