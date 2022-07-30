package com.example.android.roomyweather.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val DATABASE_NAME = "forecast-db"

@Database(entities = [ForecastLocation::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun forecastLocationDao(): ForecastLocationDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            return instance ?: synchronized(this){
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java,DATABASE_NAME)
                .build()


    }
}