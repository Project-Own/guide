package com.example.guide.ui.viewpager;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.example.guide.R;
import com.example.guide.adapters.SelectionPagerAdapter;
import com.example.guide.ui.contact.ContactFragment;
import com.example.guide.ui.home.HomeFragment;
import com.example.guide.ui.info.InfoFragment;
import com.example.guide.ui.recommendation.RecommendationFragment;
import com.google.android.material.tabs.TabLayout;

public class ViewPagerFragment extends Fragment {


    private ViewPagerViewModel mViewModel;
    ViewPager viewPager;
    TabLayout tabLayout;
    SelectionPagerAdapter adapter;
    NavController mNavController;
    public static ViewPagerFragment newInstance() {
        return new ViewPagerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mNavController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
        jump();
        View view = inflater.inflate(R.layout.view_pager_fragment, container, false);
//        BottomNavigationView navView = view.findViewById(R.id.navigation);
//        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabs);


        setupViewPager();
        return view;
       }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(ViewPagerViewModel.class);

        // TODO: Use the ViewModel
    }


    private void setupViewPager(){
        FragmentManager fm = getChildFragmentManager();
        Fragment fragment;
        adapter = new SelectionPagerAdapter(getChildFragmentManager());
        fragment = new HomeFragment();
        adapter.addFragment(fragment);
        fragment = new ContactFragment();
        adapter.addFragment(fragment);
        fragment = new RecommendationFragment();
        adapter.addFragment(fragment);
        fragment = new InfoFragment();
        adapter.addFragment(fragment);
        // adapter for viewPager
        viewPager.setAdapter(adapter);


        adapter.notifyDataSetChanged();

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_phone_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_star_black_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_info_outline_black_24dp);

        //  tabLayout.setTabRippleColor(myColorStateList);
        tabLayout.setBackgroundResource(R.color.colorPrimary);
        //tabLayout.setSelectedTabIndicatorGravity(TabLayout.INDICATOR_GRAVITY_CENTER);
        // For color text
        //tabLayout.setTabTextColors(getResources().getColor(R.color.black),getResources().getColor(R.color.white));

        // Set initial color of icons
        ColorStateList colors;
        if (Build.VERSION.SDK_INT >= 23) {
            colors = getResources().getColorStateList(R.color.tab_icon, getActivity().getTheme());
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

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
    }


    private void jump() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if (!previouslyStarted) {
            mNavController.navigate(R.id.action_nav_home_to_landingFragment);

        }
    }
}
