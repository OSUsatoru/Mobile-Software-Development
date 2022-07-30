package com.example.mediaplayer69

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mediaplayer69.data.SearchInfo
import com.example.mediaplayer69.databinding.ActivityYoutubevideoPlayerBinding
import com.example.mediaplayer69.ui.SearchInfoViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener

class YoutubeVideoPlayActivity : AppCompatActivity(), YouTubePlayerListener {

    // bind activity youtube video player
    private var _bind: ActivityYoutubevideoPlayerBinding? = null
    private val bind get() = _bind!!
    private var player: YouTubePlayer? = null
    private val viewModel: SearchInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // bind activity youtube video player layout
        _bind = ActivityYoutubevideoPlayerBinding.inflate(layoutInflater)
        setContentView(bind.root)
        val title = intent.getStringExtra("video_title")
        val description = intent.getStringExtra("video_description")
        val search = SearchInfo(title!!, System.currentTimeMillis()/1000)
        bind.tvVideoTitle.text = title
        bind.tvVideoDescription.text = description
        viewModel.addSearchInfo(search)

        lifecycle.addObserver(bind.youtubePlayer)
        bind.youtubePlayer.addYouTubePlayerListener(this)
    }
    override fun onReady(youTubePlayer: YouTubePlayer) {
        player = youTubePlayer
        val vid = intent.getStringExtra("video_id")
        vid?.let { youTubePlayer.loadVideo(it, 0f) }
    }
    //Class 'YoutubeVideoPlayActivity' is not abstract and does not implement abstract member public abstract fun onApiChange(youTubePlayer: YouTubePlayer): Unit defined in com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
    override fun onApiChange(youTubePlayer: YouTubePlayer){}
    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {}
    override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {}
    override fun onPlaybackQualityChange(youTubePlayer: YouTubePlayer, playbackQuality: PlayerConstants.PlaybackQuality) {}
    override fun onPlaybackRateChange(youTubePlayer: YouTubePlayer, playbackRate: PlayerConstants.PlaybackRate) {}
    override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {}
    override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {}
    override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {}
    override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) {}
}