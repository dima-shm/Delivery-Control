package com.shm.dim.delcontrol.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.shm.dim.delcontrol.R;
import com.shm.dim.delcontrol.adapter.ViewPagerAdapter;
import com.shm.dim.delcontrol.fragment.FragmentChat;
import com.shm.dim.delcontrol.fragment.FragmentMap;
import com.shm.dim.delcontrol.fragment.FragmentMenu;
import com.shm.dim.delcontrol.fragment.FragmentOrders;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private ViewPager mViewPager;

    private TabLayout mTabLayout;

    private int SELECTED_TAB_ICON_COLOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SELECTED_TAB_ICON_COLOR = MainActivity.this.getColor(R.color.colorAccent);
        initComponents();
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