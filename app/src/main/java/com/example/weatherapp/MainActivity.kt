package com.example.weatherapp

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.location.BaseActivityLocation
import com.example.weatherapp.utils.SessionManager
import com.example.weatherapp.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat
import javax.inject.Inject
import kotlin.system.exitProcess


class MainActivity : BaseActivityLocation() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private var weatherViewModel: WeatherViewModel? = null
    private var context: Context? = null
    var lng: Double = 0.0
    var lat: Double = 0.0

    @JvmField
    @Inject
    var viewModelFactory: ViewModelProvider.Factory? = null
    public var sessionManager: SessionManager = SessionManager.getInstance()

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setuInyection()
        setuDataBinding()
        setupMVVM()
        context = applicationContext
        getGeoLocation()
    }
    override fun onResume() {
        super.onResume()
        initLocationFetching(this@MainActivity)
    }
    override fun locationFetched(mLocal: Location,
                                 oldLocation: Location?,
                                 time: String?,
                                 locationProvider: String?) {

        super.locationFetched(mLocal, oldLocation, time, locationProvider)

        if (mLocal.altitude == 0.0 && mLocal.longitude == 0.0) {

            Toast.makeText(context, R.string.not_found, Toast.LENGTH_SHORT).show()

        }else{

            BaseApplication.mCurrentLocation = location
            BaseApplication.locationTime = time
            BaseApplication.oldLocation = oldLocation
            BaseApplication.locationProvider = locationProvider

            this.lng = location.longitude
            this.lat = location.latitude

            //Log.i("TAG", " lat $lat")
            //Log.i("TAG", " lng $lng")

        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        val menuItem = menu!!.findItem(R.id.action_refresh)

        if (menuItem != null) {
            tintMenuIcon(this@MainActivity, menuItem, R.color.colorWhite) //HERE
        }
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_refresh -> {
                Toast
                   .makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
                   .show()
                getGeoLocation()
            }
            R.id.action_exit -> exitProcess(-1)
            else -> {
            }
        }
        return true
    }

    fun setuInyection(){
        (application as BaseApplication).appComponent.inject(this)
    }
    fun setuDataBinding() {
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
    fun setupMVVM(){

        weatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel::class.java)

        weatherViewModel!!.weatherDataMutableLiveData.observe(this, Observer { weatherModel ->

            weatherModel.main?.temp = String.format("%s°C", weatherModel.main?.temp)
            weatherModel.main?.temp_max = String.format("%s°C", weatherModel.main?.temp_max)
            weatherModel.main?.temp_min = String.format("%s°C", weatherModel.main?.temp_min)

            ivWeather.setImageResource(getIconFromWeather(weatherModel.weather[0].main.toString()));

            sessionManager.setWheatherData(weatherModel)

            //Log.i("TAG", " " + sessionManager.wheatherData.name)

            activityMainBinding.weather = weatherModel


            //Log.d(TAG, "onChanged: " + weatherModel.name)
        })
        weatherViewModel!!.isLoading.observe(this, Observer { aBoolean -> Log.d(TAG, aBoolean.toString()) })
        weatherViewModel!!.error.observe(this, Observer { s -> Log.d(TAG, s) })

    }
    fun getGeoLocation(){

        //Log.i("TAG", " getGeoLocation lat $lat")
        //Log.i("TAG", " getGeoLocation lng $lng")

        if (sessionManager.wheatherData != null ){


            Log.i("TAG", " ")
            Log.i("TAG", " getGeoLocation lat $lat")
            Log.i("TAG", " getGeoLocation lng $lng")

            weatherViewModel?.onRequestLocation(
                    sessionManager.wheatherData.coord?.lat?.toDouble(),
                    sessionManager.wheatherData.coord?.lon?.toDouble())


        }else{

            val lat:String = "%.1f".format(lat).replace(",",".")
            val lng:String = "%.1f".format(lng).replace(",",".")

            Log.i("TAG", " ")
            Log.i("TAG", " getGeoLocation lat $lat")
            Log.i("TAG", " getGeoLocation lng $lng")
            Log.i("TAG", " ")

            weatherViewModel?.onRequestLocation(lat.toDouble(),lng.toDouble())
        }
        //weatherViewModel?.onRequestLocation(2.7,-60.7)
    }
    fun Double.format(digits: Int) = "%.${digits}f".format(this)
    fun getIconFromWeather(weather: String): Int {

        var weather = weather
        weather = weather.toLowerCase()

        when (weather) {
            "cloud" -> return R.drawable.zzz_weather_cloudy
            "snow" -> return R.drawable.zzz_weather_snowy
            "fog" ->  return R.drawable.zzz_weather_fog
            "lightning" -> return R.drawable.zzz_weather_lightning
            "rain" -> return R.drawable.zzz_weather_rainy
            "drizzle" ->  return R.drawable.zzz_weather_rainy
            else -> {
                return R.drawable.zzz_weather_sunny
            }
        }
   }
    fun tintMenuIcon(context: Context, item: MenuItem, @ColorRes color: Int) {
        val normalDrawable = item.icon
        val wrapDrawable = DrawableCompat.wrap(normalDrawable)
        DrawableCompat.setTint(wrapDrawable, context.resources.getColor(color))
        item.icon = wrapDrawable
    }
}


