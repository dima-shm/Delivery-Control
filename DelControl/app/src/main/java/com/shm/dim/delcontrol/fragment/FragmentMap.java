package com.shm.dim.delcontrol.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shm.dim.delcontrol.R;
import com.shm.dim.delcontrol.receiver.NetworkChangeReceiver;

import java.io.IOException;
import java.util.List;

public class FragmentMap extends Fragment implements OnMapReadyCallback {

    private MapView mMapView;

    private Geocoder mGeocoder;

    private final float CAMERA_ZOOM = 11.5f;

    public FragmentMap() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        initComponents(view, savedInstanceState);
        return view;
    }

    private void initComponents(View view, Bundle savedInstanceState) {
        initMapView(view, savedInstanceState);
        initGeocoder(view);
    }

    private void initMapView(View view, Bundle savedInstanceState) {
        mMapView = view.findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
    }

    private void initGeocoder(View view) {
        mGeocoder = new Geocoder(view.getContext());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(NetworkChangeReceiver.hasConnection(getContext())) {
            String address = "Минск";
            LatLng latLng = getLatLngByAddress(address);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.addMarker(new MarkerOptions().position(latLng).title(address));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, CAMERA_ZOOM));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    public LatLng getLatLngByAddress(String locationName) {
        Address address = getAddress(locationName);
        return new LatLng(address.getLatitude(), address.getLongitude());
    }

    private Address getAddress(String locationName) {
        List<Address> addressList = null;
        try {
            addressList  = mGeocoder.getFromLocationName(locationName, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressList.get(0);
    }

}