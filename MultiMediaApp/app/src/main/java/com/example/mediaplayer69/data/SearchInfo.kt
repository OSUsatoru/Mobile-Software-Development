package com.example.mediaplayer69.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class SearchInfo(
    @PrimaryKey
    val searchInfo: String,
    val timeStamp: Long
) : Serializable