package com.example.mediaplayer69.ui

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adamratzman.spotify.models.SpotifySearchResult
import com.example.mediaplayer69.*
import com.example.mediaplayer69.data.SearchInfo
import com.google.android.material.textfield.TextInputEditText
import com.spotify.android.appremote.api.SpotifyAppRemote
import kotlinx.coroutines.launch

class SpotifyActivity : AppCompatActivity() {
    private var mSpotifyAppRemote: SpotifyAppRemote? = null
    private lateinit var spotifyApiHandler: SpotifyApiHandler

    private lateinit var textBoxEntry: TextInputEditText
    private lateinit var textBoxButton: Button

    private lateinit var spotifyResultsRV: RecyclerView

    private val spotifySearchAdapter = SpotifySearchAdapter()
    private val viewModel: SearchInfoViewModel by viewModels()

    private lateinit var searchInfo: SearchInfo

    private val CLIENT_ID = "6f580a4cbd984df39d88d81db970df6b"
    private val CLIENT_SECRET_ID = "31b9f943b6fd452d89077ea8573e1952"
    private val REDIRECT_URI = "com.example.mediaplayer69://callback"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spotify)
        spotifyApiHandler = SpotifyApiHandler()

        lifecycleScope.launch {
            spotifyApiHandler.buildSearchApi()
        }

        textBoxEntry = findViewById(R.id.spotify_search_box)
        textBoxButton = findViewById(R.id.spotify_search_button)
        spotifyResultsRV = findViewById(R.id.spotify_search_results)
        spotifyResultsRV.layoutManager = LinearLayoutManager(this)
        spotifyResultsRV.setHasFixedSize(true)
        spotifyResultsRV.adapter = spotifySearchAdapter

        textBoxButton.setOnClickListener {
            val query = textBoxEntry.text.toString()
            if (!TextUtils.isEmpty(query)) {
                Log.d("tag", query)
                searchInfo = SearchInfo(query, System.currentTimeMillis()/1000)
                viewModel.addSearchInfo(searchInfo)
                lifecycleScope.launch {
                    val result = spotifyApiHandler.trackSearch(query)
                    val spotifyResults = spotifySearchResults(result)
                    spotifySearchAdapter.updateResultsList(spotifyResults)
                    it.hideKeyboard()
                }
            }
        }
    }

    // Credit for this API-parsing function goes to https://www.youtube.com/watch?v=mRZEVvvHwZM
    private fun spotifySearchResults(searchResults: SpotifySearchResult): MutableList<List<String>> {
        val spotifyResults: MutableList<List<String>> = mutableListOf(listOf())
        for (t in searchResults.tracks!!.items) {
            val singleResult = mutableListOf<String>()
            singleResult.add(t.artists[0].name)
            singleResult.add(t.name)

            // API returns track playback time in milliseconds so this
            // converts it to the traditional m:ss display style.
            val timeInSeconds = (t.length / 1000).toInt()
            val minutes = (timeInSeconds / 60).toInt()
            val remainderSeconds = (timeInSeconds % 60).toInt()
            var zeroString = ""
            if(remainderSeconds < 10){
                zeroString = "0"
            }
            val timeString = "$minutes:$zeroString$remainderSeconds"
            singleResult.add(timeString)
            singleResult.add(t.externalUrls.spotify.toString())
            spotifyResults.add(singleResult)
        }
        return spotifyResults
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}