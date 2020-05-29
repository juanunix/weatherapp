package com.example.weatherapp.remote

import com.example.weatherapp.model.WheatherData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/weather?")
    fun getWeatherInfoByLocation(
                       @Query("lat") lat: Double?,
                       @Query("lon") lon: Double?,
                       @Query("units") unit: String,
                       @Query("appid") appid: String?): Single<WheatherData?>?

    @GET("data/2.5/weather?")
    fun getWeatherInfoByCity(
                       @Query("q") lon: String?,
                       @Query("units") unit: String,
                       @Query("appid") appid: String?): Single<WheatherData?>?
}