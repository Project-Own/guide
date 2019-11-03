package com.example.guide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.guide.R;
import com.example.guide.adapters.SliderAdapter;

public class LandingActivity extends AppCompatActivity {

    private ViewPager mslider;
    private TextView[] mdots;
    private SliderAdapter sliderAdapter;
    private LinearLayout mdotLayout;

    private Button mNextButton;
    private Button mBackButton;

    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.CustomTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        mslider = findViewById(R.id.slider);
        mdotLayout = findViewById(R.id.dots);

        mNextButton = findViewById(R.id.next);
        mBackButton = findViewById(R.id.back);

        sliderAdapter=new SliderAdapter(this);
        mslider.setAdapter(sliderAdapter);

        addDotsIndicator(0);
        mslider.addOnPageChangeListener(viewListner);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mNextButton.getText().toString().equals("Finish")) {
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    finish();
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
    }
    public void addDotsIndicator(int position){
        mdots=new TextView[3];
        mdotLayout.removeAllViews();

        for (int i=0; i < mdots.length; i++) {

            mdots[i] = new TextView(this);
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


}

