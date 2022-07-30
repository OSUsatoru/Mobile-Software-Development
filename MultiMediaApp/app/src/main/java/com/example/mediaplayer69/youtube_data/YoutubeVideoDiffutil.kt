package com.example.mediaplayer69.youtube_data

import androidx.recyclerview.widget.DiffUtil

class YoutubeVideoDiffutil ( private val oldList: List<YoutubeVideo.VideoItem>, private val newList: List<YoutubeVideo.VideoItem>)
    : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }
    override fun getNewListSize(): Int {
        return newList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldVideo = oldList[oldItemPosition]
        val newVideo = newList[newItemPosition]
        return oldVideo.snippet.title == newVideo.snippet.title
    }
}