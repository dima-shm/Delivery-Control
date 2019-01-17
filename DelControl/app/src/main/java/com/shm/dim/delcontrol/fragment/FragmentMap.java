package com.shm.dim.delcontrol.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shm.dim.delcontrol.R;
import com.shm.dim.delcontrol.adapter.CustomInfoWindowAdapter;
import com.shm.dim.delcontrol.receiver.NetworkChangeReceiver;

import java.io.IOException;

@SuppressLint("MissingPermission")
public class FragmentMap
        extends Fragment
        implements OnMapReadyCallback {

    private Geocoder mGeocoder;

    private MapView mMapView;

    private GoogleMap mMap;

    private final float DEFAULT_CAMERA_ZOOM = 11.5f;

    private final float USER_CAMERA_ZOOM = 15f;

    private FloatingActionButton mFindUserLocation;

    private FloatingActionButton mUpdateMarkers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        initComponents(view, getArguments());
        return view;
    }

    private void initComponents(View view, Bundle savedInstanceState) {
        initGeocoder(view);
        initMapView(view, savedInstanceState);
        initFloatingActionButtons(view);
    }

    private void initGeocoder(View view) {
        mGeocoder = new Geocoder(view.getContext());
    }

    private void initMapView(View view, Bundle savedInstanceState) {
        mMapView = view.findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);
    }

    private void initFloatingActionButtons(View view) {
        mFindUserLocation = view.findViewById(R.id.find_location);
        mUpdateMarkers = view.findViewById(R.id.update_markers);
        setButtonOnClickListeners();
    }

    private void setButtonOnClickListeners() {
        mFindUserLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location userLocation = getUserLocation();
                if(userLocation != null) {
                    LatLng userPosition = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userPosition, USER_CAMERA_ZOOM));
                }
            }
        });
        mUpdateMarkers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMapView.getMapAsync(FragmentMap.this);
            }
        });
    }

    private Location getUserLocation() {
        return mMap.getMyLocation();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.getMapAsync(this);
        mMapView.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        configureMap();
        setMarkersOnMap();
        setDefaultCameraPosition();
    }

    private void configureMap() {
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setInfoWindowAdapter(getCustomInfoWindowAdapter());
    }

    private GoogleMap.InfoWindowAdapter getCustomInfoWindowAdapter() {
        return new CustomInfoWindowAdapter(getContext());
    }

    private void setMarkersOnMap() {
        if(isInternetAvailable()) {
            LatLng location = getLatLngByAddress("Минск");
            if (location != null) {
                addMarker(location, R.color.colorAccentDark,
                        "Order ID", "City, street, house number, apartment");
            }
        }
    }

    private void addMarker(LatLng location, int colorId,
                           String orderId, String orderAddress) {
        mMap.addMarker(new MarkerOptions()
                .position(location)
                .icon(getMarkerIconWithCustomColor(colorId))
                .title(orderId)
                .snippet(orderAddress));
    }

    public BitmapDescriptor getMarkerIconWithCustomColor(int colorId) {
        float[] hsv = new float[3];
        Color.colorToHSV(getResources().getColor(colorId), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    private void setDefaultCameraPosition() {
        LatLng defaultPosition = new LatLng(53.904539799999995,27.5615244);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultPosition, DEFAULT_CAMERA_ZOOM));
    }

    private boolean isInternetAvailable() {
        return NetworkChangeReceiver.isInternetAvailable(getContext());
    }

    public LatLng getLatLngByAddress(String locationName) {
        Address address = getAddress(locationName);
        if(address != null)
            return new LatLng(address.getLatitude(), address.getLongitude());
        else
            return null;
    }

    private Address getAddress(String locationName) {
        try {
            return mGeocoder.getFromLocationName(locationName, 1).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

}