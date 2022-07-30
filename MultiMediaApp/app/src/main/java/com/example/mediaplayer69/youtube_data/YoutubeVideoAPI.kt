package com.example.mediaplayer69.youtube_data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//https://square.github.io/retrofit/

interface YoutubeVideoAPI {
    @GET("search")
    fun getVideo(
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("order") order: String,
        @Query("pageToken") pageToken: String?,
        @Query("q") query: String?
    ): Call<YoutubeVideo>

}
