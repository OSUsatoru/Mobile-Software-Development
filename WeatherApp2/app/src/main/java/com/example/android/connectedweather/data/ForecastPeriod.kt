package com.example.android.connectedweather.data

import com.squareup.moshi.Json
import java.io.Serializable


data class ForecastPeriod(
    @Json(name = "dt_txt") val date: String,
    @Json(name = "pop") val pop: Double,
    @Json(name = "main") val main_info: MainInfo,
    @Json(name = "clouds") val clouds: Clouds,
    @Json(name = "weather") val weather: List<Weather>
) : Serializable

data class MainInfo(
    @Json(name = "temp_max") val highTemp: Double,
    @Json(name = "temp_min") val lowTemp: Double
): Serializable

data class Weather(
    @Json(name = "description") val shortDesc: String?
): Serializable

data class Clouds(
    @Json(name = "all") val cloudy: Double
): Serializable
