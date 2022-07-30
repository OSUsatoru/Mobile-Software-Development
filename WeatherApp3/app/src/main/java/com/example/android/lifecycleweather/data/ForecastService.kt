package com.example.android.lifecycleweather.data

import com.example.android.lifecycleweather.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//const val OPENWEATHER_APPID = BuildConfig.OPENWEATHER_API_KEY

interface ForecastService {
    @GET("/data/2.5/forecast")
    suspend fun fetchForecasts(
        @Query("q") city: String? = "Corvallis,OR,US",
        @Query("units") units: String? = "imperial",
        @Query("appid") appid: String = BuildConfig.OPENWEATHER_API_KEY
    ) : FiveDayForecast

    companion object{
        private const val BASE_URL = "https://api.openweathermap.org"
        fun create():ForecastService{
            val moshi = Moshi.Builder()
                .add(OpenWeatherListJsonAdapter())
                .add(OpenWeatherCityJsonAdapter())
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            return retrofit.create(ForecastService::class.java)
        }
    }

}