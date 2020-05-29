package com.example.weatherapp.model

class Wind {
    var deg: String? = null
    var speed: String? = null
    var gust: String? = null

    override fun toString(): String {
        return "ClassPojo [deg = $deg, speed = $speed, gust = $gust]"
    }
}