package com.example.android.basicweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

// Idea to pass all dummy data to adapter is from this YouTube: https://www.youtube.com/watch?v=UbP8E6I91NA
// Idea to make interface to implement click event for RecyclerView: https://android.suzu-sd.com/2021/03/recyclerview_item_click/
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // dummy data
        var data_array = IntArray(10){ i-> i+14}
        var high_temp_array = arrayOf(
            51,
            55,
            47,
            53,
            49,
            49,
            48,
            45,
            45,
            44
        )
        var low_temp_array = arrayOf(
            43,
            39,
            39,
            36,
            33,
            36,
            38,
            35,
            30,
            31
        )
        var precip_array = arrayOf(
            25,
            80,
            10,
            60,
            10,
            15,
            30,
            50,
            30,
            50
        )
        var short_description_array = arrayOf(
            "Mostly sunny",
            "AM showers",
            "AM fog/PM clouds",
            "AM showers",
            "Partly cloudy",
            "Partly cloudy",
            "Mostly cloudy",
            "Showers",
            "AM showers",
            "Few showers"
        )
        var long_description_array = arrayOf(
            "Mostly sunny with clouds increasing in the evening",
            "Morning showers receding in the afternoon, with patches of sun later in the day",
            "Cooler, with morning fog lifting into a cloudy day",
            "Chance of light rain in the morning",
            "Early clouds clearing as the day goes on, nighttime temperatures approaching freezing",
            "Clouds increasing throughtout the day with periods of sun interspersed",
            "Cloudy all day, with a slight chance of rain later in the evening",
            "There is no hance of light rain in the morning",
            "Cooler, with morning fog lifting into a cloudy day",
            "Few showers in the morning, highly chance of cloudy"
        )

        val weatherListRV = findViewById<RecyclerView>(R.id.rv_weather_list)
        val coordinatorLayout = findViewById<CoordinatorLayout>(R.id.coordinator_layout)

        weatherListRV.layoutManager = LinearLayoutManager(this)
        weatherListRV.setHasFixedSize(true)

        // ready to store all weather info
        var weather_Array: MutableList<Weather> = mutableListOf()

        // insert each weather info
        for(i in data_array.indices){
            val weather = Weather("Jan", data_array[i],high_temp_array[i],low_temp_array[i],precip_array[i], short_description_array[i],long_description_array[i])
            weather_Array.add(weather)
        }

        // pass all info to adapter
        val adapter = WeatherAdapter(weather_Array)
        weatherListRV.adapter = adapter


        adapter.listener = object : WeatherAdapter.OnWeatherClickListener{
            override fun onWeatherClick(weather: Weather) {
                Snackbar.make(coordinatorLayout,
                weather.long_description,
                Snackbar.LENGTH_SHORT).show()
            }
        }


    }

}