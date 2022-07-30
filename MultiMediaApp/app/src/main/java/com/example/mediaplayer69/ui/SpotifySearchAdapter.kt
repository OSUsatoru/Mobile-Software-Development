package com.example.mediaplayer69.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaplayer69.R

class SpotifySearchAdapter() : RecyclerView.Adapter<SpotifySearchAdapter.SpotifySearchViewHolder>() {
    private var spotifyResults: MutableList<List<String>> = mutableListOf()

    fun removeFirst(nums: MutableList<List<String>>?) {
        if (nums != null && nums.size > 0) {
            nums.removeAt(0)
        }
    }
    fun updateResultsList(newSpotifyResults: MutableList<List<String>>?) {
        if(newSpotifyResults != null){
            removeFirst(newSpotifyResults)
            spotifyResults = newSpotifyResults
        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = spotifyResults.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotifySearchViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_spotify_item, parent, false)
        return SpotifySearchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SpotifySearchViewHolder, position: Int) {
        holder.bind(spotifyResults[position])

        holder.itemView.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(spotifyResults[position][3]))
            holder.itemView.context.startActivity(intent)
        }
    }

    class SpotifySearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val searchItemTV: TextView = itemView.findViewById(R.id.spotify_search_item)
        private var currentSearchResults: List<String>? = null

        fun bind(currSearch: List<String>) {
            if(!currSearch.isNullOrEmpty()){
                currentSearchResults = currSearch
                searchItemTV.text = currSearch[0] + " - " + currSearch[1] + " (Time: " + currSearch[2] + ")"
            }
        }
    }
}