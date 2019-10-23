package com.example.guide;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class OfflineCapabiliy extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
