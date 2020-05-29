package com.example.weatherapp.model

class WheatherData {
    var dt: String? = null
    var coord: Coord? = null
    var timezone: String? = null
    lateinit var weather: Array<Weather>
    var name: String? = null
    var cod: String? = null
    var main: Main? = null
    var clouds: Clouds? = null
    var id: String? = null
    var sys: Sys? = null
    var base: String? = null
    var wind: Wind? = null

    override fun toString(): String {
        return "ClassPojo [dt = $dt, coord = $coord, timezone = $timezone, weather = $weather, name = $name, cod = $cod, main = $main, clouds = $clouds, id = $id, sys = $sys, base = $base, wind = $wind]"
    }
}