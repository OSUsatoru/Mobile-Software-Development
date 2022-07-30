package com.example.mediaplayer69.ui

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaplayer69.R
import com.example.mediaplayer69.data.LocalAudio

// Reference: https://rrtutors.com/tutorials/how-to-use-media-player-to-play-list-of-audio-files-in-android-studio-using-kotlin
class LocalAudioAdapter(private val context: Context, private val audioList: ArrayList<LocalAudio>) :
    RecyclerView.Adapter<LocalAudioAdapter.ViewHolder>() {
    private lateinit var mediaPlayer: MediaPlayer

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title : TextView = itemView.findViewById(R.id.title_TV)
        val icon : AppCompatImageView = itemView.findViewById(R.id.play_pause_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.local_audio, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val audio = audioList[position]

        val title = audio.title
        viewHolder.title.text = title

        viewHolder.icon.setOnClickListener {
            if (viewHolder.icon.tag.equals("play")) {
                playAudio(title)
                viewHolder.icon.tag = R.string.state_pause
                viewHolder.icon.setBackgroundResource(R.drawable.ic_pause_button)
            }
            else {
                // If music is playing, stop it
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()

                // Indicate that the audio has been paused
                Toast.makeText(context, "Audio has been paused", Toast.LENGTH_SHORT)
                    .show()
                viewHolder.icon.tag = R.string.state_play
                viewHolder.icon.setBackgroundResource(R.drawable.ic_play_button)
            }
        }
    }

    override fun getItemCount() = audioList.size

    private fun playAudio(title: String) {
        // https://developer.android.com/guide/topics/media/mediaplayer#mediaplayer
        // https://stackoverflow.com/questions/15912825/how-to-read-file-from-res-raw-by-name
        mediaPlayer = MediaPlayer.create(context, context.resources.getIdentifier(title, "raw", context.packageName))
        mediaPlayer.start()

        // Indicate that the audio started playing
        Toast.makeText(context, "Audio started playing...", Toast.LENGTH_SHORT).show()
    }
}