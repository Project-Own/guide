package com.example.guide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import com.example.guide.R;

public class PlacesDetail extends AppCompatActivity {
    MotionLayout motionLayout;
    View view;

    TextView textView;
    Boolean contentTextFullscreen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_recycler_list_detail);

        motionLayout = findViewById(R.id.motionLayout);

        view = findViewById(R.id.bgView);
        textView = findViewById(R.id.contentText);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        String description = b.getString("description", "");
        textView.setText(description);

        motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {
                if (v >= 0.95) {
                    view.setBackgroundResource(R.color.colorAccent);
                    contentTextFullscreen = true;
                } else {
                    view.setBackgroundResource(R.drawable.rectangle);
                    contentTextFullscreen = false;
                }

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        if (contentTextFullscreen) {
            motionLayout.transitionToStart();
        } else {
            super.onBackPressed();
        }
    }
}
