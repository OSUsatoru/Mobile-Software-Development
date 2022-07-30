package com.example.mediaplayer69.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaplayer69.R
import com.example.mediaplayer69.data.LocalAudio

class LocalFilesActivity : AppCompatActivity() {
    private lateinit var localAudioRV: RecyclerView
    private lateinit var localAudioAdapter: LocalAudioAdapter
    private var localAudioList = ArrayList<LocalAudio>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_files)

        localAudioRV = findViewById(R.id.local_files_RV)
        localAudioRV.layoutManager = LinearLayoutManager(this)

        // Load audio list with audio files in assets/audio
        assets.list("audio")?.forEach {
            localAudioList.add(LocalAudio(it.substringBeforeLast(".mp3", "").lowercase()))
        }

        localAudioAdapter = LocalAudioAdapter(this, localAudioList)
        localAudioRV.adapter = localAudioAdapter
    }
}