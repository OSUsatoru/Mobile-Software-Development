package com.example.android.roomyweather.data


import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.time.LocalDateTime

/**
 * This class represents a response from the OpenWeather API's Five Day Forecast API:
 *
 * https://openweathermap.org/forecast5
 */

data class FiveDayForecast(
    @Json(name = "list") val periods: List<ForecastPeriod>,

    val city: ForecastCity

)

@Entity
data class ForecastLocation(
    @PrimaryKey val city_name: String,
    val cur_time: Long
)