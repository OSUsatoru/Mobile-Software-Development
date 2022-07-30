package com.example.android.basicweather

data class Weather(
    var month: String,
    var date: Int,
    var high_temp: Int,
    var low_temp: Int,
    var precip_per: Int,
    var short_description: String,
    var long_description: String
)
