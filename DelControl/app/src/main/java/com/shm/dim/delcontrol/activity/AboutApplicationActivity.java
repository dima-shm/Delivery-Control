package com.shm.dim.delcontrol.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.shm.dim.delcontrol.R;

public class AboutApplicationActivity extends AppCompatActivity {

    private TextView versionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_application);
        initVersionTextView();
        setVersionText();
    }

    private void initVersionTextView() {
        versionText = findViewById(R.id.version_text);
    }

    private void setVersionText() {
        versionText.setText(getVersionName());
    }

    private String getVersionName() {
        String versionName = "";
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

}