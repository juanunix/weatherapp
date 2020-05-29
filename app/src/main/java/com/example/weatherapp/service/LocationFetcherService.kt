package com.example.weatherapp.service

import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.Build
import android.os.IBinder
import com.example.weatherapp.listener.LocationManagerInterface
import com.example.weatherapp.location.SmartLocationManager
import com.example.weatherapp.service.LocationFetcherService
import com.google.android.gms.common.ConnectionResult

class LocationFetcherService : Service(), LocationManagerInterface {
    private var smartLocationManager: SmartLocationManager? = null
    private var lat = 0.0
    private var lon = 0.0
    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        initLocationFetching()
        return START_STICKY
    }

    inner class LocationBinder : Binder() {
        val service: LocationFetcherService
            get() = this@LocationFetcherService
    }

    fun initLocationFetching() {
        try {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!SmartLocationManager.checkPermission(this)) {
                        SmartLocationManager.requestPermission(this) { SmartLocationManager.startInstalledAppDetailsActivity(applicationContext) }
                    } else {
                        smartLocationManager = SmartLocationManager(this, SmartLocationManager.FETCH_LOCATION_ONCE, this, SmartLocationManager.ALL_PROVIDERS, 2 * 1000, 1 * 1000, SmartLocationManager.LOCATION_PROVIDER_ALL_RESTRICTION, SmartLocationManager.ANY_API) // init location manager
                    }
                } else {
                    smartLocationManager = SmartLocationManager(this, SmartLocationManager.FETCH_LOCATION_ONCE, this, SmartLocationManager.ALL_PROVIDERS, 2 * 1000, 1 * 1000, SmartLocationManager.LOCATION_PROVIDER_ALL_RESTRICTION, SmartLocationManager.ANY_API) // init location manager
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun locationFetched(mLocation: Location, oldLocation: Location, time: String, locationProvider: String) {
        lat = mLocation.latitude
        lon = mLocation.longitude
    }

    override fun onLocationNotEnabled(message: String) {}
    override fun onPermissionDenied(message: String) {}
    override fun onLocationFetchingFailed(failureType: Int, connectionResult: ConnectionResult) {}
    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        smartLocationManager!!.abortLocationFetching()
    }

    companion object {
        private const val SERVICE_PERIOD = 5 * 1000.toLong()
    }
}