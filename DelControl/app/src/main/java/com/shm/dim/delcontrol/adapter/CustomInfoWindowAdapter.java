package com.shm.dim.delcontrol.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.shm.dim.delcontrol.R;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private View mView;

    private TextView mOrderId;

    private TextView mOrderAddress;

    public CustomInfoWindowAdapter(Context context) {
        mView = ((Activity)context).getLayoutInflater().inflate(R.layout.info_window, null);
        initComponents();
    }

    private void initComponents() {
        mOrderId = mView.findViewById(R.id.info_window_order_id);
        mOrderAddress = mView.findViewById(R.id.info_window_order_address);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public View getInfoWindow(final Marker marker) {
        mOrderId.setText(marker.getTitle());
        mOrderAddress.setText(marker.getSnippet());
        return mView;
    }

}