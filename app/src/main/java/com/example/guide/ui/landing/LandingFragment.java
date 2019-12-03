package com.example.guide.ui.landing;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.example.guide.R;
import com.example.guide.adapters.SliderAdapter;

public class LandingFragment extends Fragment {

    private LandingViewModel mViewModel;

    private ViewPager mslider;
    private TextView[] mdots;
    private SliderAdapter sliderAdapter;
    private LinearLayout mdotLayout;

    private Button mNextButton;
    private Button mBackButton;

    private int mCurrentPage;
    private Toolbar toolbar;

    public static LandingFragment newInstance() {
        return new LandingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.landing_fragment, container, false);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        mslider = v.findViewById(R.id.slider);
        mdotLayout = v.findViewById(R.id.dots);

        mNextButton = v.findViewById(R.id.next);
        mBackButton = v.findViewById(R.id.back);

        sliderAdapter=new SliderAdapter(getContext());
        mslider.setAdapter(sliderAdapter);

        addDotsIndicator(0);
        mslider.addOnPageChangeListener(viewListner);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mNextButton.getText().toString().equals("Finish")) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
                    edit.commit();
                    NavController navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
                    navController.navigate(R.id.action_landingFragment_to_nav_home);
                   } else {

                    mslider.setCurrentItem(mCurrentPage + 1);
                }
            }
        });


        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mslider.setCurrentItem(mCurrentPage - 1);

            }
        });


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LandingViewModel.class);
        // TODO: Use the ViewModel
    }

    public void addDotsIndicator(int position){
        mdots=new TextView[4];
        mdotLayout.removeAllViews();

        for (int i=0; i < mdots.length; i++) {

            mdots[i] = new TextView(getContext());
            mdots[i].setText(Html.fromHtml("&#8226;"));
            mdots[i].setTextSize(35);
            mdots[i].setTextColor(getResources().getColor(R.color.colorPrimary));

            mdotLayout.addView(mdots[i]);

        }
        if (mdots.length>0){
            mdots[position].setTextColor(getResources().getColor(R.color.white));
        }

    }

    ViewPager.OnPageChangeListener viewListner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);

            mCurrentPage = position;

            if(position == 0){

                mNextButton.setEnabled(true);
                mBackButton.setEnabled(false);
                mBackButton.setVisibility(View.INVISIBLE);

                mNextButton.setText("Next");
                mBackButton.setText("");

            } else if (position == mdots.length-1){

                mNextButton.setEnabled(true);
                mBackButton.setEnabled(true);
                mBackButton.setVisibility(View.VISIBLE);

                mNextButton.setText("Finish");
                mBackButton.setText("Back");

            } else {

                mNextButton.setEnabled(true);
                mBackButton.setEnabled(true);
                mBackButton.setVisibility(View.VISIBLE);

                mNextButton.setText("Next");
                mBackButton.setText("Back" );

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onDestroy() {
        toolbar.setVisibility(View.VISIBLE);
        super.onDestroy();
    }
}
