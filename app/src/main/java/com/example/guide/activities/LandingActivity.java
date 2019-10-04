package com.example.guide.activities;

import android.os.Bundle;
import android.text.Html;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        mslider=(ViewPager)findViewById(R.id.slider);
        mdotLayout = findViewById(R.id.dots);
        sliderAdapter=new SliderAdapter(this);
        mslider.setAdapter(sliderAdapter);
        addDots(0);
        mslider.addOnPageChangeListener(viewListner);
    }
    public void addDots(int position){
        mdots=new TextView[3];
        for (int i=0;i<mdots.length;i++) {
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
        ViewPager.OnPageChangeListener viewListner =new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addDots(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };


    }

