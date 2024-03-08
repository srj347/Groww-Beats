package com.example.growwbeats.data.services

import com.example.growwbeats.data.entities.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SpotifyMusicService {

    @GET("search")
    suspend fun getSearchResult(
        @Query("q") query: String,
        @Query("type") type: String
    ): SearchResponse


}