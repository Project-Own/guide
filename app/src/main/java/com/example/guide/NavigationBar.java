package com.example.guide;

import android.content.Context;
import android.os.Build;
import android.view.MenuItem;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.guide.ui.about.AboutFragment;
import com.example.guide.ui.calendar.CalendarFragment;
import com.example.guide.ui.forex.ForexFragment;
import com.example.guide.ui.gallery.GalleryFragment;
import com.example.guide.ui.offlineMap.OfflineMapFragment;
import com.example.guide.ui.translation.TranslateFragment;
import com.example.guide.ui.viewpager.ViewPagerFragment;
import com.example.guide.ui.weather.WeatherFragment;
import com.google.android.material.navigation.NavigationView;

public class NavigationBar implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private FragmentManager fragmentManager;
    private DrawerLayout drawerLayout;

    public NavigationBar(Context context, DrawerLayout drawerLayout, FragmentManager fragmentManager) {
        this.context = context;
        this.drawerLayout = drawerLayout;
        this.fragmentManager = fragmentManager;
    }
    public NavigationBar(Context context, DrawerLayout drawerLayout, String className) {
        this.context = context;
        this.drawerLayout = drawerLayout;
        this.fragmentManager = fragmentManager;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment destination = new ViewPagerFragment();
        if (id == R.id.nav_gallery) {
            destination = new GalleryFragment();
        } else if (id == R.id.nav_calendar) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                destination = new CalendarFragment();
            }
        } else if (id == R.id.nav_currency) {
            destination = new ForexFragment();
        } else if (id == R.id.nav_share) {
            destination = new AboutFragment();
        } else if (id == R.id.nav_detail) {
            destination = new AboutFragment();
        } else if (id == R.id.nav_map) {
            destination = new OfflineMapFragment();
        } else if (id == R.id.nav_weather) {
            destination = new WeatherFragment();
        } else if (id == R.id.nav_translator){
            destination = new TranslateFragment();
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        fragmentManager.beginTransaction()
                .replace(R.id.my_nav_host_fragment, destination)
                .addToBackStack(null)
                .commit();

//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        boolean previouslyStarted = prefs.getBoolean(context.getString(R.string.nav_bar_is_selected), false);
//        if(previouslyStarted){
//            fragmentManager.beginTransaction()
//                    .replace(R.id.my_nav_host_fragment, destination)
//                    .commit();
//
//        }else{
//            fragmentManager.beginTransaction()
//                    .replace(R.id.my_nav_host_fragment, destination)
//                    .addToBackStack("backstack")
//                    .commit();
//
//            SharedPreferences.Editor edit = prefs.edit();
//            edit.putBoolean(context.getString(R.string.nav_bar_is_selected), Boolean.TRUE);
//            edit.commit();
//        }
        return true;
    }
}
