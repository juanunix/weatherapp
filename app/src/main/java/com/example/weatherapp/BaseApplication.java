package com.example.weatherapp;

import android.app.Application;
import android.content.Context;
import android.location.Location;

import com.example.weatherapp.di.components.AppComponent;
import com.example.weatherapp.di.components.DaggerAppComponent;


public class BaseApplication extends Application {

    private AppComponent appComponent;

    public static Location mCurrentLocation;
    public static String locationProvider;
    public static Location oldLocation;
    public static String locationTime;
    public static Context mContext;


    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }
    public static void setInstance(BaseApplication instance) {
        BaseApplication.instance = instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        initApplication();
        appComponent= DaggerAppComponent.create();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    private void initApplication() {
        mContext = getApplicationContext();
    }
}


