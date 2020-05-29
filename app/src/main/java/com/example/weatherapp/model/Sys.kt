package com.example.weatherapp.model

class Sys {
    var country: String? = null
    var sunrise: String? = null
    var sunset: String? = null
    var id: String? = null
    var type: String? = null

    override fun toString(): String {
        return "ClassPojo [country = $country, sunrise = $sunrise, sunset = $sunset, id = $id, type = $type]"
    }
}