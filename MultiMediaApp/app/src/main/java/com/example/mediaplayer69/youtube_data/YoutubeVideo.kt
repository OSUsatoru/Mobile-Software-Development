package com.example.mediaplayer69.youtube_data

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class YoutubeVideo(
    val nextPageToken: String?,
    val items: List<VideoItem>
) {
    data class VideoItem (
        @SerializedName ("id") val v_Id: VideoId,
        val snippet: YoutubeSnippet
    )
    data class VideoId(
        @SerializedName ("videoId") val id: String
    )
}