package com.shm.dim.delcontrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shm.dim.delcontrol.R;

public class MenuListAdapter extends ArrayAdapter<String> {

    private final Context mContext;

    private final String[] mItemNames;

    private final Integer[] mItemImageIds;

    private TextView mMenuItemName;

    private ImageView mMenuItemImage;

    public MenuListAdapter(Context context, String[] itemNames, Integer[] itemImageIds) {
        super(context, R.layout.menu_item, itemNames);
        mContext = context;
        mItemNames = itemNames;
        mItemImageIds = itemImageIds;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.menu_item, null,true);
        initComponents(rowView);
        setContentInComponents(position);
        return rowView;
    }

    private void initComponents(View view) {
        initItemName(view);
        initItemImage(view);
    }

    private void initItemName(View view) {
        mMenuItemName = view.findViewById(R.id.menu_item_name);
    }

    private void initItemImage(View view) {
        mMenuItemImage = view.findViewById(R.id.menu_item_image);
    }

    private void setContentInComponents(int position) {
        setContentInItemName(position);
        setContentInItemImage(position);
    }

    private void setContentInItemName(int position) {
        mMenuItemName.setText(mItemNames[position]);
    }

    private void setContentInItemImage(int position) {
        mMenuItemImage.setImageResource(mItemImageIds[position]);
    }

}