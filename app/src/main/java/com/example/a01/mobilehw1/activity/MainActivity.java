package com.example.a01.mobilehw1.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a01.mobilehw1.R;
import com.example.a01.mobilehw1.util.Constants;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_USERNAME = 100;
    public static final String USERNAME_KEY = "userNameKey";

    private TextView mUsernameTv;
    private Button favoriteBtn;
    private ImageView mStarIv;
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        parseIntent();
        initButtons();
        initIcons();
        initTextComponents();
    }

    private void parseIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mUsername = intent.getStringExtra(USERNAME_KEY);
        }
    }

    private void initButtons() {
        favoriteBtn = findViewById(R.id.btn_favorite);
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteArticle();
            }
        });
    }


    private void initIcons(){
        mStarIv = findViewById(R.id.iv_main_star);

        if(!getFavoriteSelection()) {
            mStarIv.setImageResource(R.drawable.star_border);
        }
        else{
            mStarIv.setImageResource(R.drawable.star);
        }
    }

    private boolean getFavoriteSelection(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getBoolean(Constants.FAVORITE_PREFERENCE_KEY, false);
    }

    private void persistFavoriteSelection(){
        if(favoriteBtn.getText().equals(getString(R.string.unfavorite))){
            persistBooleanAttribute(false, Constants.FAVORITE_PREFERENCE_KEY);
        }
        else{
            persistBooleanAttribute(true, Constants.FAVORITE_PREFERENCE_KEY);
        }
    }

    private void initTextComponents() {
        mUsernameTv = findViewById(R.id.tv_main_username);
        mUsernameTv.setText("Welcome " + mUsername + " here's an article of the day, do you like it?");
        TextView mArticleTv = findViewById(R.id.tv_main_article);
        mArticleTv.setText("Clanak koji moze da se skroluje.");
    }

    private void favoriteArticle(){
        persistFavoriteSelection();

        if(favoriteBtn.getText().equals(getString(R.string.unfavorite))){
            mStarIv.setImageResource(R.drawable.star_border);
            favoriteBtn.setText(getString(R.string.favorite));
        }
        else{
            mStarIv.setImageResource(R.drawable.star);
            favoriteBtn.setText(getString(R.string.unfavorite));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != REQUEST_CODE_USERNAME) {
            return;
        }

        if (resultCode == RESULT_OK) {
            presentUsername(data);
            Timber.e("OK %s", requestCode);
        } else if (resultCode == RESULT_CANCELED) {
            showToast(getString(R.string.no_changes));
            Timber.e("CANCELED %s", requestCode);
        } else {
            Timber.e("Something went wrong");
        }
    }

    private void presentUsername(Intent intent) {
        if (intent != null) {
            mUsername = intent.getStringExtra(USERNAME_KEY);
        }
        mUsernameTv.setText(mUsername);
    }


    private void persistBooleanAttribute( boolean preference,String  preferenceKey) {
        String packageName = getPackageName();
        SharedPreferences sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(preferenceKey, preference);
        editor.apply();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
