package com.example.growwbeats.data.repositories

import android.util.Log
import com.example.growwbeats.data.entities.SearchResponse
import com.example.growwbeats.data.sources.RemoteSpotifyDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MusicRepository(
    val remoteSource: RemoteSpotifyDataSource
): Repository {
    private val TAG = MusicRepository::class.simpleName

    suspend fun getSearchResult(query: String, type: String) = withContext(Dispatchers.IO){
        var response: SearchResponse? = null
        try {
            response = remoteSource.getSearchResult(query, type)
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
        return@withContext response
    }

    suspend fun getAuthToken() = withContext(Dispatchers.IO){
        return@withContext remoteSource.getAuthToken()
    }

}