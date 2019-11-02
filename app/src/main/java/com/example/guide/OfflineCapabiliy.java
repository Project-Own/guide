package com.example.guide;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.FirebaseDatabase;

public class OfflineCapabiliy extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Glide.with(getApplicationContext())
                .asGif()
                .load(R.raw.splash_logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .preload()
        ;

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
