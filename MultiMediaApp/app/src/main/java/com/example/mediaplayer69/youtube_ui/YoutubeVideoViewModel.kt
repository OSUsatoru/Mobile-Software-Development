package com.example.mediaplayer69.youtube_ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mediaplayer69.youtube_data.YoutubeVideo
import com.example.mediaplayer69.youtube_data.YoutubeVideoData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YoutubeVideoViewModel : ViewModel() {

    private val _video = MutableLiveData<YoutubeVideo?>()
    val video = _video
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading
    private val _isLoaded = MutableLiveData<Boolean>()
    val isLoaded = _isLoaded
    private val _message = MutableLiveData<String>()
    var nextPageToken: String? = null
    var querySearch: String? = null
    init {
        getVideo()
    }
    fun getVideo(){
        _isLoading.value = true
        val client = YoutubeVideoData
            .getService()
            .getVideo(
                "snippet",
                "",
                "date",
                nextPageToken,
                querySearch
            )
        client.enqueue(object : Callback<YoutubeVideo> {
            override fun onResponse(call: Call<YoutubeVideo>, response: Response<YoutubeVideo>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val data = response.body()
                    if (data != null){
                        if (data.nextPageToken != null){
                            nextPageToken = data.nextPageToken
                        } else {
                            _isLoaded.value = true
                        }
                        if (data.items.isNotEmpty()){
                            _video.value = data
                        }
                    } else {
                        _message.value = "No video"
                    }
                } else {
                    _message.value = response.message()
                }
            }
            override fun onFailure(call: Call<YoutubeVideo>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Failed: ", t)
                _message.value = t.message
            }
        })
    }
    companion object {
        private val TAG = YoutubeVideoViewModel::class.java.simpleName
    }

}