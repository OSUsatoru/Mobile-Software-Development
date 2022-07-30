package com.example.android.lifecycleweather.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class ForecastRepository(
    private val service: ForecastService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {//Result<List<ForecastPeriod>>
    suspend fun loadRepositoriesFetch(city:String?, units: String?) : Result<FiveDayForecast> =
        withContext(ioDispatcher){
            try{
                val results = service.fetchForecasts(city,units)
                Result.success(results)
            }catch(e:Exception){
                Result.failure(e)
            }

        }
}