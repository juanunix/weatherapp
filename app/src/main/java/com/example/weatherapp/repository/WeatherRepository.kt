package com.example.weatherapp.repository

import com.example.weatherapp.model.WheatherData
import com.example.weatherapp.remote.WeatherService
import io.reactivex.Single
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherService: WeatherService) {

    var appid = "b394d23c308439258c1981c22a2c73b2"
    var units = "metric"

    fun getWeatherInfoByLocation(lat: Double?, lon: Double?): Single<WheatherData?>? {
        return weatherService.getWeatherInfoByLocation( lat, lon, units, appid)
    }

    fun getWeatherInfoByCity(city: String?): Single<WheatherData?>? {
        return weatherService.getWeatherInfoByCity(city,units, appid)
    }

}