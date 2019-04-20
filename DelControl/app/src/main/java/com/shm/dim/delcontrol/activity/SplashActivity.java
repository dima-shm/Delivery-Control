package com.shm.dim.delcontrol.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.shm.dim.delcontrol.R;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME = 3 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //startActivityAfterTimer(MainActivity.class, SPLASH_TIME);
        startActivityAfterTimer(RegistrationActivity.class, SPLASH_TIME);
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

}