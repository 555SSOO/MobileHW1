package com.example.a01.mobilehw1.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a01.mobilehw1.R;
import com.example.a01.mobilehw1.util.Constants;

public class LoginActivity extends AppCompatActivity {

    private EditText mLoginUsernameEt;
    private EditText mLoginPasswordEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        mLoginUsernameEt = findViewById(R.id.et_login_name);
        mLoginPasswordEt = findViewById(R.id.et_password);
        Button loginBtn = findViewById(R.id.btn_login_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String username = mLoginUsernameEt.getText().toString();
        String password = mLoginPasswordEt.getText().toString();
        if (username.isEmpty()){
            Toast.makeText(this, getString(R.string.enter_username), Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()){
            Toast.makeText(this, getString(R.string.enter_username), Toast.LENGTH_SHORT).show();
            return;
        }
        persistAttribute(username, Constants.USERNAME_PREFERENCE_KEY);
        persistAttribute(password, Constants.PASSWORD_PREFERENCE_KEY);
        startMainActivity(username);
    }

    private void startMainActivity(String username) {

        Intent intent = new Intent(this, MainActivity.class);

        //Directly, no Bundle
        intent.putExtra(MainActivity.USERNAME_KEY, username);


        //With bundle
//        Bundle bundle = new Bundle();
//        bundle.putString(MainActivity.USERNAME_KEY, username);
//        intent.putExtras(bundle);

        startActivity(intent);
        // We want to kill and remove LoginActivity from back stack
        finish();
    }

    private void persistAttribute(String preference, String preferenceKey) {
        // First parameter is preference object id and it should be same as app's
        // package name, although it's not necessary
        // MODE_PRIVATE means that only our app can access it

        String packageName = getPackageName();
        SharedPreferences sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(preferenceKey, preference);

        // Saves data on ui thread
        // editor.commit();

        // Saves data on background thread
        editor.apply();

        // Friendly advice, duplicating all this code all over the app is not the best practice,
        // Consider creating util class and expose red/write methods for values you want to read/store

    }
}
