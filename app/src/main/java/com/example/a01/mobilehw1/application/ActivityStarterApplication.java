package com.example.a01.mobilehw1.application;

import android.app.Application;

import timber.log.Timber;

public class ActivityStarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
