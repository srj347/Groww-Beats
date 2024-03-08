package com.example.growwbeats.data.sources

import com.example.growwbeats.data.entities.AuthTokenResponse
import com.example.growwbeats.data.entities.SearchResponse
import com.example.growwbeats.data.services.SpotifyAuthService
import com.example.growwbeats.data.services.SpotifyMusicService

class RemoteSpotifyDataSource(
    private val spotifyMusicApi: SpotifyMusicService,
    private val spotifyAuthApi: SpotifyAuthService
) {
    suspend fun getSearchResult(query: String, type: String): SearchResponse {
        return spotifyMusicApi.getSearchResult(query, type)
    }

    suspend fun getAuthToken(): AuthTokenResponse {
        return spotifyAuthApi.getAuthToken()
    }
}