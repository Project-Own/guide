package com.example.guide.ui.splash;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.guide.R;

public class SplashFragment extends Fragment {

    private SplashViewModel mViewModel;
    private ImageView imageView;
    NavController mNavController;

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.splash_fragment, container, false);

        imageView = view.findViewById(R.id.imageViewSplash);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mNavController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                jump();
            }
        },1000);
    }

    private void jump() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if (!previouslyStarted) {


            mNavController.navigate(R.id.action_splashFragment2_to_landingFragment);

        }else {
            mNavController.navigate(R.id.action_splashFragment2_to_nav_home);
        }
    }




}
