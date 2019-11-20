package com.example.guide.ui.review;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.guide.R;

public class ReviewFragment extends Fragment {

    RatingBar ratingBar;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ratingBar= (RatingBar) findViewById(R.id.rating);
        button= findViewById(R.id.button);


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getContext(),"Stars: ", Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Stars: "+ ratingBar.getRating(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
