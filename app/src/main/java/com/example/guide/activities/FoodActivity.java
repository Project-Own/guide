package com.example.guide.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Modal.Places;
import com.example.guide.NavigationBar;
import com.example.guide.R;
import com.example.guide.adapters.PlacesAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {


    Activity activity;
    Context context;
    private RecyclerView recyclerView;
    private List<Places> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        context = this;
        activity = FoodActivity.this;

        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationBar(context, drawer, this.getClass().getSimpleName()));

        recyclerView = findViewById(R.id.places_recyclerView);
        foodList = new ArrayList<>();
        foodList.add(new Places("Choila", "-Boneless and skinless buffalo meat which is roasted in fire " +
                "(fire made  of straw).marinated with the secret home made newari spices  ", "choila"));

        foodList.add(new Places("Chatamari", "a pan baked thin newari bread;the batter prepared with rice flour" +
                ",spread over a pan thinly covered for few seconds and not turned", "chatamari"));

        foodList.add(new Places("Bara", "a special dish prepared with lentils " +
                "(green lentil, black lentil and chick peas lentil). the lentil is soaked in the water overnight and ground, hence " +
                "using the traditional tool for grinding spices it is ground and baked in pan using mustard oil. ", "bara"));

        foodList.add(new Places("Kachila", "Dish prepared with minced meat which is marinated in homemade newari" +
                "spices and mustard oil","kachila"));

        PlacesAdapter adapter = new PlacesAdapter(foodList, recyclerView, context, activity);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
