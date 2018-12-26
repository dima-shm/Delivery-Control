package com.shm.dim.delcontrol.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.shm.dim.delcontrol.R;
import com.shm.dim.delcontrol.adapter.ViewPagerAdapter;
import com.shm.dim.delcontrol.fragment.FragmentChat;
import com.shm.dim.delcontrol.fragment.FragmentMap;
import com.shm.dim.delcontrol.fragment.FragmentOrders;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private ViewPager mViewPager;

    private TabLayout mTabLayout;

    private int SELECTED_TAB_COLOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SELECTED_TAB_COLOR = MainActivity.this.getColor(R.color.colorPrimaryDark);
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
        initViewPagerAdapter(adapter);
        mViewPager.setAdapter(adapter);
    }

    private void initViewPagerAdapter(ViewPagerAdapter adapter ) {
        adapter.addFragment(new FragmentMap());
        adapter.addFragment(new FragmentOrders());
        adapter.addFragment(new FragmentChat());
    }

    private void initTabLayout() {
        mTabLayout = findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        initMainTab(1, SELECTED_TAB_COLOR);
        setOnTabSelectedListener();
    }

    private void initMainTab(int index, int color) {
        TabLayout.Tab tab = mTabLayout.getTabAt(index);
        tab.select();
        setOnTabIconColor(tab, color);
    }

    private void setOnTabSelectedListener() {
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setOnTabIconColor(tab, SELECTED_TAB_COLOR);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mTabLayout.getTabAt(0).getIcon().clearColorFilter();
                tab.getIcon().clearColorFilter();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setOnTabIconColor(TabLayout.Tab tab, int color) {
        tab.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_change_account) {
            Toast.makeText(this, "Выход из акаунтта и переход к окну входа/регистрации",
                    Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}