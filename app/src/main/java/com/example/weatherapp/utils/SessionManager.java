package com.example.weatherapp.utils;

import android.content.Context;
import android.util.Log;

import com.example.weatherapp.BaseApplication;
import com.example.weatherapp.model.WheatherData;
import com.google.gson.Gson;



public class SessionManager {

    private static final String TAG = SessionManager.class.getSimpleName();

    private WheatherData wheatherData;
    private PreferenceManager mPrefs;
    private Context context;

    private static SessionManager ourInstance;

    public SessionManager(Context context) {
        this.context = context;
        mPrefs = new PreferenceManager(context);
    }

    public static SessionManager getInstance() {
        if (ourInstance == null) {
            return ourInstance = new SessionManager(BaseApplication.getInstance().getBaseContext());
        } else {
            return ourInstance;
        }
    }

    public void setWheatherData(WheatherData wheatherData) {
        Gson gson = new Gson();

        String userResponseString = gson.toJson(wheatherData);
        mPrefs.putString(Constants.WEATHER_KEY, userResponseString);
        this.wheatherData = wheatherData;

        Log.i("TAG", userResponseString);
    }

    public WheatherData getWheatherData() {
        Gson gson = new Gson();

        WheatherData wheatherData = gson.fromJson(mPrefs.getString(
                Constants.WEATHER_KEY, null), WheatherData.class);

        if (wheatherData == null) {
            wheatherData = null;
        }

        return wheatherData;
    }


}

