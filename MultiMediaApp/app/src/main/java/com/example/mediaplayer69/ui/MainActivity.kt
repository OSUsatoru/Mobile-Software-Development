package com.example.mediaplayer69.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mediaplayer69.R
import com.example.mediaplayer69.YoutubeVideoActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val youtubeButton: Button = findViewById(R.id.youtube_button)
        youtubeButton.setOnClickListener{
            val intent = Intent(this, YoutubeVideoActivity::class.java)
            startActivity(intent)
            true
        }

        val localFilesButton: Button = findViewById(R.id.local_files_button)
        localFilesButton.setOnClickListener {
            // Go to LocalFilesActivity
            onlocalFilesButtonClick()
        }

        val spotifyButton: Button = findViewById(R.id.spotify_button)
        spotifyButton.setOnClickListener {
            // Go to SpotifyActivity
            onSpotifyButtonClick()
        }

        val historyButton: Button = findViewById(R.id.history_button)
        historyButton.setOnClickListener {
            // Go to HistoryActivity
            onHistoryButtonClick()
        }
    }

    private fun onSpotifyButtonClick() {
        val intent = Intent(this, SpotifyActivity::class.java).apply {
        }
        startActivity(intent)
    }

    private fun onHistoryButtonClick() {
        val intent = Intent(this, HistoryActivity::class.java).apply {
        }
        startActivity(intent)
    }

    private fun onlocalFilesButtonClick() {
        val intent = Intent(this, LocalFilesActivity::class.java).apply {
        }
        startActivity(intent)
    }
}