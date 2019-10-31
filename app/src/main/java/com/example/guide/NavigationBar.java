package com.example.guide;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.guide.activities.CalendarActivity;
import com.example.guide.activities.FoodActivity;
import com.example.guide.activities.ForexActivity;
import com.example.guide.activities.GalleryActivity;
import com.example.guide.activities.MainActivity;
import com.example.guide.activities.MapsActivity;
import com.example.guide.activities.PlacesActivity;
import com.example.guide.activities.WeatherActivity;
import com.google.android.material.navigation.NavigationView;

public class NavigationBar implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private String className;
    private DrawerLayout drawerLayout;

    public NavigationBar(Context context, DrawerLayout drawerLayout, String className) {
        this.context = context;
        this.drawerLayout = drawerLayout;
        this.className = className;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            if (!className.equals("MainActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(intent);
            }
        } else if (id == R.id.nav_gallery) {
            if (!className.equals("GalleryActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), GalleryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(intent);
            }
        } else if (id == R.id.nav_calendar) {
            if (!className.equals("CalendarActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), CalendarActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(intent);
            }
        } else if (id == R.id.nav_currency) {
            if (!className.equals("ForexActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), ForexActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                context.startActivity(intent);
            }
        } else if (id == R.id.nav_share) {
            if (!className.equals("MainActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), ForexActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                context.startActivity(intent);
            }
        } else if (id == R.id.nav_detail) {
            if (!className.equals("MainActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                context.startActivity(intent);

            }
        } else if (id == R.id.nav_map) {
            if (!className.equals("MapsActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), MapsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                context.startActivity(intent);
            }
        } else if (id == R.id.nav_place) {
            if (!className.equals("PlacesActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), PlacesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                context.startActivity(intent);
            }
        } else if (id == R.id.nav_food) {
            if (!className.equals("FoodActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), FoodActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(intent);
            }
        } else if (id == R.id.nav_weather) {
            if (!className.equals("WeatherActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), WeatherActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                context.startActivity(intent);
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);


        return true;
    }
}
