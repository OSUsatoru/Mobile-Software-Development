package com.example.mediaplayer69

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
// Resources for Youtube API
// https://developers.google.com/youtube/android/player/reference/com/google/android/youtube/player/YouTubePlayerFragment
// https://www.youtube.com/watch?v=qI1H3idRN8c
// https://developers.google.com/youtube/android/player/reference/com/google/android/youtube/player/YouTubePlayer
// https://developers.google.com/youtube/v3/docs/search/list

class YoutubeVideoActivity : AppCompatActivity(){
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.youtube_main_activity)
                val navController = findNavController(R.id.nav_host_fragment)
        }
        override fun onCreateOptionsMenu(menu: Menu): Boolean {
                menuInflater.inflate(R.menu.youtubevideosearch_menu, menu)
                return true
        }
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
                return when(item.itemId){
                        R.id.menu_search -> false
                        else -> false
                }

        }
}