package com.example.android.roomyweather.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.roomyweather.data.AppDatabase
import com.example.android.roomyweather.data.BookmarkedForecast
import com.example.android.roomyweather.data.ForecastLocation
import kotlinx.coroutines.launch

class BookmarkedForecastViewModel(application: Application) : AndroidViewModel(application){

    private val forecast = BookmarkedForecast(
        AppDatabase.getInstance(application).forecastLocationDao()
    )

    val bookmarkedForecasts = forecast.getAllBookmarkedForecasts().asLiveData()

    fun addBookmarkedForecast(fore: ForecastLocation){
        viewModelScope.launch {
            forecast.insertBookmarkedForecast(fore)
        }
    }

    fun removeBookmarkedForecast(fore: ForecastLocation){
        viewModelScope.launch {
            forecast.removeBookmarkedForecast(fore)
        }
    }

    //fun getBookmarkedForecastByName(name: String) = forecast.getBookmarkedForecastsByName(name).asLiveData()

}