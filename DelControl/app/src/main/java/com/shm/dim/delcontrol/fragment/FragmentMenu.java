package com.shm.dim.delcontrol.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.shm.dim.delcontrol.R;
import com.shm.dim.delcontrol.adapter.MenuListAdapter;

public class FragmentMenu extends Fragment {

    private ListView mMenuItemsList;

    private Spinner mCourierStatus;

    private String[] menuItemNames;

    private Integer[] mMenuItemImageIds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        initMenuItemArrays();
        initMenuItemsList(view);
        setMenuListAdapter(view);
        setMenuItemsListClickListener();
        initCourierStatus(view);
        setCourierStatusSelectedListener();
    }

    private void initMenuItemArrays() {
        menuItemNames = getResources().getStringArray(R.array.menu_names);
        mMenuItemImageIds = new Integer[] {
                R.drawable.change_marker_color,
                R.drawable.edit_account,
                R.drawable.log_out_of_account
        };
    }

    private void initMenuItemsList(View view) {
        mMenuItemsList = view.findViewById(R.id.menu_items_list);
    }

    private void setMenuListAdapter(View view) {
        MenuListAdapter adapter = new MenuListAdapter(view.getContext(), menuItemNames, mMenuItemImageIds);
        mMenuItemsList.setAdapter(adapter);
    }

    private void setMenuItemsListClickListener() {
        mMenuItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemName = menuItemNames[position];
                Toast.makeText(view.getContext(), selectedItemName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initCourierStatus(View view) {
        mCourierStatus = view.findViewById(R.id.courier_status);
    }

    private void setCourierStatusSelectedListener() {
        mCourierStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                String[] courierStatus = getResources().getStringArray(R.array.courier_status);
                String statusName = courierStatus[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

}