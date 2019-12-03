package com.example.guide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.example.guide.R;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public SliderAdapter(Context context){
        this.context=context;
    }
    public String[] slide_images={
            "conversation",
            "map_location",
            "exchange",
            "calendar",

    };

    public String[] slide_headings = {
            "Information",
            "Navigate",
            "Forex",
            "Calendar"
    };

    public String[] slide_desc={
            "Understand Local Heritage and Culture",
            "Navigate through Street of Bhaktapur",
            "Know your rate",
            "Pre-plan your stay"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =layoutInflater.inflate(R.layout.slider_layout,container,false);
        LottieAnimationView slideImageView=view.findViewById(R.id.imageView2);
        TextView slideTextView1=view.findViewById(R.id.textView);
        TextView slideTextView=view.findViewById(R.id.textView3);
        slideTextView.setText(slide_headings[position]);
        slideTextView1.setText(slide_desc[position]);
        slideImageView.setAnimation(context.getResources()
                .getIdentifier(slide_images[position], "raw", context.getPackageName()));
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) object);

    }
}
