package com.example.mediaplayer69.youtube_data


data class YoutubeSnippet(
    val title: String,
    val description: String,
    val customUrl: String,
    val publishedAt: String,
    val thumbnails: YoutubeThumbnail,
    val position: Int?
)