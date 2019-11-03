package com.example.guide.activities;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Modal.Users;
import com.example.guide.NavigationBar;
import com.example.guide.R;
import com.example.guide.adapters.AboutAdapter;
import com.example.guide.adapters.PlacesAdapter;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimator;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends AppCompatActivity {


    AppCompatActivity activity;
    Context context;
    private RecyclerView recyclerView;
    private List<Users> usersList;
    private SpringyAdapterAnimator springyAdapterAnimator;
    private PlacesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.CustomTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        context = this;
        activity = AboutActivity.this;

        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationBar(context, drawer, this.getClass().getSimpleName()));

        recyclerView = findViewById(R.id.places_recyclerView);
        usersList = new ArrayList<>();
        usersList.add(new Users("NIRAJAN PRJAPATI", "KHCE 074 BCT 024", "prajapatinirajan0@gmail.com", "ruko"));
        usersList.add(new Users("NIRJAL PRAJAPATI", "KHCE 074 BCT 026", "nirjalprajapati@gmail.com", "nirjal"));
        usersList.add(new Users("ROHIT PRAJAPATI", "KHCE074 BCT 033", "roht.praz@gmail.com", "rohit"));
        usersList.add(new Users("SAHAS PRAJAPATI", "KHCE074 BCT 037", "sahas_1999@hotmail.com", "sahas"));

        AboutAdapter adapter = new AboutAdapter(usersList, getApplicationContext());
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
