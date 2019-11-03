package com.example.guide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.guide.R;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public SliderAdapter(Context context){
        this.context=context;
    }
    public int[] slide_images={
            R.drawable.eat_icon,
            R.drawable.sleep_icon,
            R.drawable.code_icon
    };

    public String[] slide_headings = {"Information",
            "Navigate",
            "Understand",
    };

    public String[] slide_desc={
            "Be Informative:\nForeign Exchange Rate",
            "Navigate Through Maps and Markers",
            "Understand Local Heritage and Culture"
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
        View view =layoutInflater.inflate(R.layout.activity_slider,container,false);
        ImageView slideImageView=view.findViewById(R.id.imageView2);
        TextView slideTextView1=view.findViewById(R.id.textView);
        TextView slideTextView=view.findViewById(R.id.textView3);
        slideImageView.setImageResource(slide_images[position]);
        slideTextView.setText(slide_headings[position]);
        slideTextView1.setText(slide_desc[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) object);

    }
}
