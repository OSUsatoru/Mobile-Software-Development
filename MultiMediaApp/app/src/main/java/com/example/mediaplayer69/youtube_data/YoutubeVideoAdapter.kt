package com.example.mediaplayer69.youtube_data

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mediaplayer69.YoutubeVideoPlayActivity
import com.example.mediaplayer69.databinding.YoutubeVideoItemBinding

class YoutubeVideoAdapter
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var oldItems = ArrayList<YoutubeVideo.VideoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = YoutubeVideoItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(oldItems[position])
    }

    class ViewHolder(itemView: YoutubeVideoItemBinding) : RecyclerView.ViewHolder(itemView.root){
        private val binding = itemView

        fun bind(data: YoutubeVideo.VideoItem){

            binding.root.setOnClickListener {
                // to player
                val intent = Intent(it.context, YoutubeVideoPlayActivity::class.java)
                intent.putExtra("video_id", data.v_Id.id)
                intent.putExtra("video_title", data.snippet.title)
                intent.putExtra("video_description", data.snippet.description)
                it.context.startActivity(intent)
            }

            binding.tvVideoTitle.text = data.snippet.title
            binding.tvPublished.text = data.snippet.publishedAt
            Glide.with(binding.root)
                .load(data.snippet.thumbnails.high.url)
                .into(binding.ivThumbnail)
        }

    }

    override fun getItemCount(): Int {
        return oldItems.size
    }

    fun setData(newList: List<YoutubeVideo.VideoItem>, rv: RecyclerView){
        val videoDiff = YoutubeVideoDiffutil(oldItems, newList)

        val diff = DiffUtil.calculateDiff(videoDiff)
        oldItems.addAll(newList)
        diff.dispatchUpdatesTo(this)
        rv.scrollToPosition(oldItems.size - newList.size)
    }

    fun clearAll(){
        oldItems.clear()
        notifyDataSetChanged()
    }

}