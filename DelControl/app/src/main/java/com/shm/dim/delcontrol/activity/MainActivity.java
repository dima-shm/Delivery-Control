package com.shm.dim.delcontrol.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.shm.dim.delcontrol.R;
import com.shm.dim.delcontrol.adapter.ViewPagerAdapter;
import com.shm.dim.delcontrol.asyncTask.RestRequestDelegate;
import com.shm.dim.delcontrol.asyncTask.RestRequestTask;
import com.shm.dim.delcontrol.fragment.FragmentChat;
import com.shm.dim.delcontrol.fragment.FragmentMap;
import com.shm.dim.delcontrol.fragment.FragmentMenu;
import com.shm.dim.delcontrol.fragment.FragmentOrders;
import com.shm.dim.delcontrol.receiver.NetworkChangeReceiver;
import com.shm.dim.delcontrol.service.LocationService;

import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private ViewPager mViewPager;

    private TabLayout mTabLayout;

    private int SELECTED_TAB_ICON_COLOR;

    private final int REQUEST_PERMISSION_CODE = 100;

    private NetworkChangeReceiver mNetworkReceiver = new NetworkChangeReceiver();

    private BroadcastReceiver mLocationReceiver;

    private SharedPreferences mSharedPreferences;

    private static final String AССOUNT_PREFERENCES = "ACCOUNT_INFO",
            AССOUNT_ID = "AССOUNT_ID",
            AССOUNT_NAME = "AССOUNT_NAME";

    private String mCourierId, mLatitude, mLongitude, mSpeed, mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SELECTED_TAB_ICON_COLOR = MainActivity.this.getColor(R.color.colorAccent);
        initComponents();
        checkPermissions();
        startLocationService();
    }

    private void initComponents() {
        initToolbar();
        initViewPager();
        initTabLayout();
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initViewPager() {
        mViewPager = findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        addFragmentsToAdapter(adapter);
        mViewPager.setAdapter(adapter);
    }

    private void addFragmentsToAdapter(ViewPagerAdapter adapter ) {
        adapter.addFragment(new FragmentMap());
        adapter.addFragment(new FragmentOrders());
        adapter.addFragment(new FragmentChat());
        adapter.addFragment(new FragmentMenu());
    }

    private void initTabLayout() {
        mTabLayout = findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        initMainTab(1, SELECTED_TAB_ICON_COLOR);
        setOnTabSelectedListener();
    }

    private void initMainTab(int index, int color) {
        TabLayout.Tab tab = mTabLayout.getTabAt(index);
        tab.select();
        setColorOnTabIcon(tab, color);
    }

    private void setOnTabSelectedListener() {
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setColorOnTabIcon(tab, SELECTED_TAB_ICON_COLOR);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mTabLayout.getTabAt(0).getIcon().clearColorFilter();
                tab.getIcon().clearColorFilter();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }

        });
    }

    private void setColorOnTabIcon(TabLayout.Tab tab, int color) {
        tab.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    private void startLocationService() {
        Intent intent = new Intent(getApplicationContext(), LocationService.class);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSION_CODE) {
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                checkPermissions();
            }
        }
    }

    public void checkPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNetworkReceiver();
        registerLocationReceiver();
    }

    private void registerNetworkReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkReceiver, filter);
    }

    private void registerLocationReceiver() {
        mLocationReceiver = getLocationReceiver();
        registerReceiver(mLocationReceiver, new IntentFilter("LOCATION_UPDATE"));
    }

    private BroadcastReceiver getLocationReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mLatitude = intent.getExtras().get("LATITUDE").toString();
                mLongitude = intent.getExtras().get("LONGITUDE").toString();
                mSpeed = intent.getExtras().get("SPEED").toString();
                mTime = intent.getExtras().get("TIME").toString();
                sendCourierInfo();
            }
        };
    }

    private void sendCourierInfo() {
        String body = getJsonUserInfo();
        if(body != null) {
            sendRestRequest("http://192.168.43.234:46002/api/CourierInfoes/",
                    "PUT",
                    body);
        }
    }

    protected String getJsonUserInfo() {
        initUserInfValues();
        if(mLatitude == null || mLongitude == null)
            return null;
        return "{" +
                "'CourierId':'" + mCourierId + "'," +
                "'Latitude':'" + mLatitude + "'," +
                "'Longitude':'" + mLongitude + "'," +
                "'Speed':'" + mSpeed + "'," +
                "'Time':'" + mTime + "'" +
                "}";
    }

    protected void initUserInfValues() {
        mSharedPreferences = getSharedPreferences(AССOUNT_PREFERENCES, Context.MODE_PRIVATE);
        mCourierId = mSharedPreferences.getString(AССOUNT_ID, "");
        String[] locationInfo = getLocationInfo();
        mLatitude = locationInfo[0];
        mLongitude = locationInfo[1];
        mSpeed = locationInfo[2];
        mTime = locationInfo[3];
    }

    protected void sendRestRequest(String url, String method, String body) {
        RestRequestTask request =
                new RestRequestTask(new RestRequestDelegate() {
                    @Override
                    public void executionFinished(int responseCode, String responseBody) {
                        onRestRequestFinished(responseCode, responseBody);
                    }
                });
        request.execute(url, method, body);
    }

    private void onRestRequestFinished(int responseCode, String responseBody) {
        if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            Toast.makeText(this, getResources().getString(R.string.complete),
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,
                    getResources().getString(R.string.check_network_state) +
                            " (code: " + String.valueOf(responseCode) + ")",
                    Toast.LENGTH_LONG).show();
        }
    }

    public String[] getLocationInfo() {
        String[] locationInfo = new String[4];
        locationInfo[0] = mLatitude;
        locationInfo[1] = mLongitude;
        locationInfo[2] = mSpeed;
        locationInfo[3] = mTime;
        return locationInfo;
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mNetworkReceiver);
        unregisterReceiver(mLocationReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationService();
    }

    private void stopLocationService() {
        Intent intent = new Intent(getApplicationContext(), LocationService.class);
        stopService(intent);
    }

}