package com.example.growwbeats.data.repositories

import com.example.growwbeats.data.entities.Track
import com.example.growwbeats.data.sources.LocalSpotifyDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.ArrayList

class RecentSearchRepository(
    private val localDataStore: LocalSpotifyDataSource
) : Repository {
    suspend fun fetchRecentSearchResult(): ArrayList<Track>? = withContext(Dispatchers.IO) {
        return@withContext localDataStore.fetchRecentTracks() as? ArrayList<Track>
    }

    suspend fun insertRecentTrack(track: Track) = withContext(Dispatchers.IO) {
        localDataStore.insertRecentTrack(track)
    }

    suspend fun findRecentTracksByName(searchQuery: String): List<Track> =
        withContext(Dispatchers.IO) {
            return@withContext localDataStore.findRecentTracksByName("%$searchQuery%")
        }
}