package com.example.android.roomyweather.data

class BookmarkedForecast(
    private val dao: ForecastLocationDao
) {
    suspend fun insertBookmarkedForecast(fore: ForecastLocation) = dao.insert(fore)
    suspend fun removeBookmarkedForecast(fore: ForecastLocation) = dao.delete(fore)

    fun getAllBookmarkedForecasts() = dao.getAllForecasts()

    //fun getBookmarkedForecastsByName(name: String) = dao.getForecastByName(name)

}