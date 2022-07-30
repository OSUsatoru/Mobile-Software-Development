package com.example.mediaplayer69.ui

import com.adamratzman.spotify.SpotifyAppApi
import com.adamratzman.spotify.models.SpotifySearchResult
import com.adamratzman.spotify.spotifyAppApi
import com.adamratzman.spotify.utils.Market

// Credit to this idea of a ApiHandler goes to https://www.youtube.com/watch?v=mRZEVvvHwZM, and also Adamratzman's kotlin package

class SpotifyApiHandler {
    private val clientID = "6f580a4cbd984df39d88d81db970df6b"
    private val clientSecret = "31b9f943b6fd452d89077ea8573e1952"
    private var api: SpotifyAppApi? = null

    /// Pulls the developer ClientID and ClientSecret tokens provided
    /// by Spotify and builds them into an object that can easily
    /// call public Spotify APIs.
    suspend fun buildSearchApi() {
        api = spotifyAppApi(clientID, clientSecret).build()
    }

    // Performs Spotify database query for queries related to track information. Returns
    // the results as a SpotifySearchResult object.
    suspend fun trackSearch(searchQuery: String): SpotifySearchResult {
        return api!!.search.searchAllTypes(searchQuery, 50, 1, market = Market.US)
    }

}