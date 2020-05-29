package com.example.weatherapp.listener;

import android.location.Location;

import com.google.android.gms.common.ConnectionResult;


public interface LocationManagerInterface {

    void locationFetched(Location mLocation, Location oldLocation, String time, String locationProvider);

    void onLocationFetchingFailed(int failureType, ConnectionResult connectionResult);

    void onLocationNotEnabled(String message);

    void onPermissionDenied(String message);
}
