package com.example.mediaplayer69.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchInfo: SearchInfo)

    @Delete
    suspend fun delete(searchInfo: SearchInfo)

    @Query("SELECT * FROM SearchInfo ORDER BY timeStamp DESC")
    fun getSearchInfo() : Flow<MutableList<SearchInfo>>
}