package com.example.a01.mobilehw1.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import com.example.a01.mobilehw1.R;
import com.example.a01.mobilehw1.util.Constants;

/*
* Creating splash screen this way is an anti pattern!
* This is made only to demonstrate how backstack works.
* We'll create splash screen the right way later in the course.
* */

public class SplashActivity extends AppCompatActivity {

    private static final int DELAY_MILLIS = 1500;
    private static final String URL = "https://picsum.photos/1080/1920/?random";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {

        ImageView imageView = findViewById(R.id.iv_main_content);
        Picasso.get()
                .load(URL)
                .into(imageView);

        startLoginActivity();
        //handleWelcomeFlow();
    }

    private void handleWelcomeFlow() {
        // Check comments for SharedPreferences on LoginActivity.java
        String packageName = getPackageName();
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        String username = sharedPreferences.getString(Constants.USERNAME_PREFERENCE_KEY, null);

        if (username == null) {
            // User has never entered his username, so we know that he is first time in the app
            // and we want to show him login screen
            startActivityWithDelay(LoginActivity.class);
        } else {
            // User has already saved username, so we know that he is already oppened the app,
            // we should send him to the main activity
            startActivityWithDelay(MainActivity.class);
        }
    }

    private void startLoginActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                // We want to kill and remove SplashActivity from back stack
                finish();
            }
        }, DELAY_MILLIS);
    }

    // Always try to make your methods more generic, try not to duplicate code
    // You can pass class you want to start in this method and delete method above
    private void startActivityWithDelay(final Class cls) {
        // Using handler we can execute parts of our code with delay
        // We'll get back to it sometime later, don't worry about it now
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, cls);
                startActivity(intent);
                // We want to kill and remove SplashActivity from back stack
                finish();
            }
        }, DELAY_MILLIS);
    }


}
