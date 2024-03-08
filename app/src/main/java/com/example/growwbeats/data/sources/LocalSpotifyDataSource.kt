package com.example.growwbeats.data.sources

import com.example.growwbeats.data.entities.Track
import com.example.growwbeats.data.services.RecentSearchDao

class LocalSpotifyDataSource(
     private val dao: RecentSearchDao
) {
    suspend fun fetchRecentTracks(): List<Track> {
        return dao.getRecentTracks()
    }

    suspend fun insertRecentTrack(track: Track){
        if (dao.getRecentTracksCount() >= 10) {
            dao.deleteOldestTrack()
        }
        dao.insertRecentTracks(track)
    }

    suspend fun findRecentTracksByName(searchQuery: String): List<Track> {
        return dao.findRecentTracksByName(searchQuery)
    }
}
