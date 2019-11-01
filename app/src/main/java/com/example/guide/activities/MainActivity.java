package com.example.guide.activities;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.guide.R;
import com.example.guide.adapters.SelectionPagerAdapter;
import com.example.guide.fragments.ContactFragment;
import com.example.guide.fragments.HomeFragment;
import com.example.guide.fragments.InfoFragment;
import com.example.guide.fragments.RecomendationFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Context context;
    private Button navbarButton;
    NavigationView navigationView;
    DrawerLayout drawer;
    ObjectAnimator scaleDown;
    ColorStateList myColorStateList = new ColorStateList(
            new int[][]{
                    new int[]{android.R.attr.state_pressed}, //1
                    new int[]{android.R.attr.state_focused}, //2
                    new int[]{android.R.attr.state_focused, android.R.attr.state_pressed} //3
            },
            new int[]{
                    Color.RED, //1
                    Color.GREEN, //2
                    Color.BLUE //3
            }
    );
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;



        navbarButton = findViewById(R.id.navbarButton);
        drawer = findViewById(R.id.drawer_layout1);
        navigationView = findViewById(R.id.nav_view);
        scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                navbarButton,
                PropertyValuesHolder.ofFloat("scaleX", 1.1f),
                PropertyValuesHolder.ofFloat("scaleY", 1.1f));
        scaleDown.setDuration(1000);

        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);


        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        navbarButton.setOnClickListener(v -> {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        scaleDown.start();
        setupViewPager();


        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION};
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, 112);
            } else {

            }
        } else {

        }
    }
    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;

    }


    private void setupViewPager(){
        // adapter for viewPager
        FragmentManager fm = getSupportFragmentManager();
        SelectionPagerAdapter adapter = new SelectionPagerAdapter(getSupportFragmentManager());



        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new ContactFragment());
        adapter.addFragment(new RecomendationFragment());
        adapter.addFragment(new InfoFragment());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);


        //  tabLayout.setTabRippleColor(myColorStateList);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        //tabLayout.setSelectedTabIndicatorGravity(TabLayout.INDICATOR_GRAVITY_CENTER);
        // For color text
        //tabLayout.setTabTextColors(getResources().getColor(R.color.black),getResources().getColor(R.color.white));

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_phone_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_star_black_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_info_outline_black_24dp);
        // Set initial color of icons
        ColorStateList colors;
        if (Build.VERSION.SDK_INT >= 23) {
            colors = getResources().getColorStateList(R.color.tab_icon, getTheme());
        } else {
            colors = getResources().getColorStateList(R.color.tab_icon);
        }

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            Drawable icon = tab.getIcon();

            if (icon != null) {
                icon = DrawableCompat.wrap(icon);
                DrawableCompat.setTintList(icon, colors);
            }
        }

        // Change color of indicator bar
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryDark));

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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        String className = this.getClass().getSimpleName();
        Log.i("ClassName", className);
        if (id == R.id.nav_home) {
            if (!className.equals("MainActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                context.startActivity(intent);
            }
        } else if (id == R.id.nav_gallery) {
            if (!className.equals("GalleryActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), GalleryActivity.class);
                context.startActivity(intent);
            }
        } else if (id == R.id.nav_calendar) {
            if (!className.equals("CalendarActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), CalendarActivity.class);
                context.startActivity(intent);
            }
        } else if (id == R.id.nav_currency) {
            if (!className.equals("ForexActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), ForexActivity.class);
                context.startActivity(intent);
            }
        } else if (id == R.id.nav_share) {
            if (!className.equals("MainActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), ForexActivity.class);
                context.startActivity(intent);
            }
        } else if (id == R.id.nav_detail) {
            if (!className.equals("AboutActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), AboutActivity.class);
                context.startActivity(intent);

            }
        } else if (id == R.id.nav_map) {
            if (!className.equals("MapsActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), MapsActivity.class);
                context.startActivity(intent);
            }
        } else if (id == R.id.nav_place) {
            if (!className.equals("PlacesActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), PlacesActivity.class);
                context.startActivity(intent);
            }
        } else if (id == R.id.nav_food) {
            if (!className.equals("FoodActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), FoodActivity.class);

                context.startActivity(intent);
            }
        } else if (id == R.id.nav_weather) {
            if (!className.equals("WeatherActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), WeatherActivity.class);
                context.startActivity(intent);
            }
        }

        drawer.closeDrawer(GravityCompat.START);


        return true;
    }


}
