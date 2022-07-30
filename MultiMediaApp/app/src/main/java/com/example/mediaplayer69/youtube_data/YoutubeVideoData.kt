package com.example.mediaplayer69.youtube_data

import com.example.mediaplayer69.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class YoutubeVideoData {
    companion object {
        fun getService(): YoutubeVideoAPI {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
                )
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val url = chain
                        .request()
                        .url
                        .newBuilder()
                        .addQueryParameter("key", BuildConfig.YOUTUBE_API_KEY)
                        .build()
                    chain.proceed(chain.request().newBuilder().url(url).build())
                }
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(YoutubeVideoAPI::class.java)

        }
    }
}