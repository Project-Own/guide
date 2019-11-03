package com.example.guide.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.bumptech.glide.Glide;
import com.example.guide.R;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
          //  VideoView videoHolder = new VideoView(this);
            //setContentView(videoHolder);
            setContentView(R.layout.activity_splash_screen);

            imageView = findViewById(R.id.imageViewSplash);


            Glide.with(this)
                    .asGif()
                    .load(R.raw.splash_logo)
                    .into(imageView)

            ;


        } catch (Exception ex) {
            jump();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        jump();
        return true;
    }

    private void jump() {
        if (isFinishing())
            return;


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
            edit.commit();
            startActivity(new Intent(this, LandingActivity.class));

        }else{

            Intent myanim = new Intent(this, MainActivity.class);

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, "splash");


            startActivity(myanim, options.toBundle());


        }
        finish();


    }
}
