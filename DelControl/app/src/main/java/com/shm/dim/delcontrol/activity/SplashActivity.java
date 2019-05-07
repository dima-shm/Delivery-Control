package com.shm.dim.delcontrol.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.shm.dim.delcontrol.R;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME = 3 * 1000;

    private SharedPreferences mSharedPreferences;

    private static final String AССOUNT_PREFERENCES = "ACCOUNT_INFO",
            AССOUNT_ID = "AССOUNT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if(!isAccountExist())
            startActivityAfterTimer(RegistrationActivity.class, SPLASH_TIME);
        else
            startActivityAfterTimer(MainActivity.class, SPLASH_TIME);
    }

    private void startActivityAfterTimer(final Class<?> cls, int time) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, cls);
                startActivity(intent);
                finish();
            }
        }, time);
    }

    private boolean isAccountExist() {
        mSharedPreferences = getSharedPreferences(AССOUNT_PREFERENCES, Context.MODE_PRIVATE);
        return mSharedPreferences.contains(AССOUNT_ID);
    }

}