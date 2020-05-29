package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.WheatherData
import com.example.weatherapp.repository.WeatherRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val modelWeatherDataMutableLiveData = MutableLiveData<WheatherData>()

    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()

    val weatherDataMutableLiveData: MutableLiveData<WheatherData>
        get() {
            //loadLocationData()
            return modelWeatherDataMutableLiveData
        }

    fun onRequestLocation(lat: Double?, lon: Double?) {
        loadLocationData(lat, lon)
    }


    private fun loadLocationData(lat: Double?, lon: Double?) {

        isLoading.value = true

        /*var citi: String = "boavista"

        weatherRepository.getWeatherInfoByCity(citi)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeWith(object : DisposableSingleObserver<WheatherData?>() {

                    override fun onSuccess(weatherData: WheatherData) {
                        isLoading.value = false
                        Log.i("TAG", " Country $weatherData")
                        modelWeatherDataMutableLiveData.value = weatherData
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.message
                        isLoading.value = false
                    }
                })?.let { disposable.add(it) }*/


        weatherRepository.getWeatherInfoByLocation(lat, lon)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeWith(object : DisposableSingleObserver<WheatherData?>() {
                    override fun onSuccess(weatherData: WheatherData) {
                        isLoading.value = false
                        Log.i("TAG", " Country $weatherData")
                        modelWeatherDataMutableLiveData.value = weatherData
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.message
                        isLoading.value = false
                    }
                })?.let { disposable.add(it) }


    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}