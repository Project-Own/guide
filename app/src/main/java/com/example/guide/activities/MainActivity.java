package com.example.guide.activities;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.guide.R;
import com.example.guide.adapters.SelectionPagerAdapter;
import com.example.guide.fragments.ContactFragment;
import com.example.guide.fragments.HomeFragment;
import com.example.guide.fragments.InfoFragment;
import com.example.guide.fragments.RecomendationFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Context context;
    private Button navbarButton;
    NavigationView navigationView;
    DrawerLayout drawer;
    ObjectAnimator scaleDown;
    ViewPager viewPager;
    TabLayout tabLayout;
    private ImageView imageView;
    MediaPlayer song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.CustomTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageViewSplash);

        context = MainActivity.this;
        song = MediaPlayer.create(MainActivity.this, R.raw.song);
        song.start();
        Glide.with(context)
                .asGif()
                .load(R.raw.splash_logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder_logo)
                .into(imageView)

        ;

        jump();



        navbarButton = findViewById(R.id.navbarButton);
        drawer = findViewById(R.id.drawer_layout1);
        navigationView = findViewById(R.id.nav_view);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabs);


        new BackgroundRun().execute();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
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

                imageView.setVisibility(View.GONE);



            }
        }, 7000);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(context, "Permission Required may be required!!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();


    }
    @Override
    protected void onPause() {
        super.onPause();
        song.release();
    }



    private void setupViewPager(){
        // adapter for viewPager
        FragmentManager fm = getSupportFragmentManager();
        SelectionPagerAdapter adapter = new SelectionPagerAdapter(getSupportFragmentManager());



        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new ContactFragment());
        adapter.addFragment(new RecomendationFragment());
        adapter.addFragment(new InfoFragment());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        //  tabLayout.setTabRippleColor(myColorStateList);
        tabLayout.setBackgroundResource(R.color.colorPrimary);
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
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryLight));

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
        else if (id == R.id.translator) {
            if (!className.equals("TranslationActivity")) {
                Intent intent = new Intent(context.getApplicationContext(), TranslationActivity.class);
                context.startActivity(intent);
            }
        }

        drawer.closeDrawer(GravityCompat.START);


        return true;
    }

    private void jump() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if (!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
            edit.commit();
            startActivity(new Intent(this, LandingActivity.class));
            finish();

        }


    }

    public class BackgroundRun extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            setupViewPager();
            scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                    navbarButton,
                    PropertyValuesHolder.ofFloat("scaleX", 1.1f),
                    PropertyValuesHolder.ofFloat("scaleY", 1.1f));
            scaleDown.setDuration(1000);


            return null;
        }

        @Override
        protected void onPreExecute() {
            imageView.animate()
                    .alpha(0f)
                    .setDuration(3000);
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }


}
