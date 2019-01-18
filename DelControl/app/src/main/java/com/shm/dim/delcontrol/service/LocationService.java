package com.shm.dim.delcontrol.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

@SuppressLint("MissingPermission")
public class LocationService extends Service {

    private LocationListener mLocationListener;

    private LocationManager mLocationManager;

    private Intent mLocationUpdateIntent;

    private Location mLocation;

    private static final long MIN_TIME = 1000 * 60 * 1;

    private static final long MIN_DISTANCE = 5;

    private static boolean isServiceActive;

    @Override
    public void onCreate() {
        initLocationListener();
        initLocationManager();
        isServiceActive = true;
    }

    private void initLocationListener() {
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                sendBroadcast(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }

            @Override
            public void onProviderEnabled(String provider) { }

            @Override
            public void onProviderDisabled(String provider) { }
        };
    }

    private void initLocationManager() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME,
                MIN_DISTANCE,
                mLocationListener);
    }

    private void sendBroadcast(Location location) {
        mLocationUpdateIntent = new Intent("LOCATION_UPDATE");
        mLocationUpdateIntent.putExtra("LATITUDE", location.getLatitude());
        mLocationUpdateIntent.putExtra("LONGITUDE", location.getLongitude());
        mLocationUpdateIntent.putExtra("SPEED", location.getSpeed());
        sendBroadcast(mLocationUpdateIntent);
    }

    public Location getLocation() {
        mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return mLocation;
    }

    public static boolean isServiceActive() {
        return isServiceActive;
    }

    @Override
    public void onDestroy() {
        mLocationManager.removeUpdates(mLocationListener);
        isServiceActive = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}