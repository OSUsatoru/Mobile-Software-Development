package com.example.android.roomyweather.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forecast: ForecastLocation)

    @Delete
    suspend fun delete(forecast: ForecastLocation)

    @Query("SELECT * FROM ForecastLocation ORDER BY cur_time DESC")
    fun getAllForecasts() : Flow<List<ForecastLocation>>

    //@Query("SELECT * FROM ForecastLocation WHERE name = :name" )
    //fun getForecastByName(name: String): Flow<ForecastLocation?>

}