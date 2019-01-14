package com.shm.dim.delcontrol.activity;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.shm.dim.delcontrol.R;
import com.shm.dim.delcontrol.adapter.ViewPagerAdapter;
import com.shm.dim.delcontrol.fragment.FragmentChat;
import com.shm.dim.delcontrol.fragment.FragmentMap;
import com.shm.dim.delcontrol.fragment.FragmentMenu;
import com.shm.dim.delcontrol.fragment.FragmentOrders;
import com.shm.dim.delcontrol.receiver.NetworkChangeReceiver;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private ViewPager mViewPager;

    private TabLayout mTabLayout;

    private int SELECTED_TAB_ICON_COLOR;

    private final int REQUEST_PERMISSION_CODE = 100;

    private NetworkChangeReceiver mNetworkReceiver = new NetworkChangeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SELECTED_TAB_ICON_COLOR = MainActivity.this.getColor(R.color.colorAccent);
        initComponents();
        checkPermissions();
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
    }

    private void registerNetworkReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mNetworkReceiver);
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

}