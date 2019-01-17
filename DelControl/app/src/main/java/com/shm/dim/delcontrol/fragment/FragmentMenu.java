package com.shm.dim.delcontrol.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.shm.dim.delcontrol.R;
import com.shm.dim.delcontrol.activity.AboutApplicationActivity;

public class FragmentMenu extends Fragment {

    private Spinner mCourierStatus;

    private Switch mLocationTracking;

    private LinearLayout mEditAccountMenuItem;

    private LinearLayout mLogoutOfAccountMenuItem;

    private LinearLayout mAboutApplicationMenuItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        initMenuItems(view);
        initCourierStatus(view);
        initLocationTrackingSwitch(view);
    }

    private void initMenuItems(View view) {
        mEditAccountMenuItem = view.findViewById(R.id.edit_account_menu_item);
        mLogoutOfAccountMenuItem = view.findViewById(R.id.log_out_of_account_menu_item);
        mAboutApplicationMenuItem = view.findViewById(R.id.about_application_menu_item);
        setMenuItemsClickListener();
    }

    private void setMenuItemsClickListener() {
        mEditAccountMenuItem.setOnClickListener(getMenuItemClickListener());
        mLogoutOfAccountMenuItem.setOnClickListener(getMenuItemClickListener());
        mAboutApplicationMenuItem.setOnClickListener(getMenuItemClickListener());
    }

    private View.OnClickListener getMenuItemClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startClickAnimation(view);
                onMenuItemClick(view);
            }
        };
    }

    private void startClickAnimation(View view) {
        Animation itemClick = AnimationUtils.loadAnimation(getContext(), R.anim.menu_item_click);
        view.startAnimation(itemClick);
    }

    private void onMenuItemClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.edit_account_menu_item : {
                //intent = new Intent(getActivity(), NewActivity.class);
            } break;
            case R.id.log_out_of_account_menu_item :{
                //intent = new Intent(getActivity(), NewActivity.class);
            } break;
            case R.id.about_application_menu_item : {
                intent = new Intent(getActivity(), AboutApplicationActivity.class);
            } break;
        }
        if(intent != null) {
            startActivity(intent);
        }
    }

    private void initCourierStatus(View view) {
        mCourierStatus = view.findViewById(R.id.courier_status);
        setCourierStatusSelectedListener();
    }

    private void initLocationTrackingSwitch(View view) {
        mLocationTracking = view.findViewById(R.id.switch_location_tracking);
    }

    private void setCourierStatusSelectedListener() {
        mCourierStatus.setOnItemSelectedListener(getCourierStatusSelectedListener());
    }

    private AdapterView.OnItemSelectedListener getCourierStatusSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                String[] courierStatus = getResources().getStringArray(R.array.courier_status);
                String statusName = courierStatus[position];
                mLocationTracking.setChecked((statusName != getLastItem(courierStatus)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        };
    }

    private String getLastItem(String[] array) {
        return array[array.length - 1];
    }

}