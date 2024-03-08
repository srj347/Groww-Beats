package com.example.growwbeats.data.services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.growwbeats.data.entities.Track

@Dao
interface RecentSearchDao {
    @Query("SELECT * FROM tracks ORDER BY searchedAt DESC")
    suspend fun getRecentTracks(): List<Track>

    @Query("SELECT COUNT(*) FROM tracks")
    suspend fun getRecentTracksCount(): Int

    @Query("DELETE FROM tracks WHERE id = (SELECT id FROM tracks ORDER BY searchedAt ASC LIMIT 1)")
    suspend fun deleteOldestTrack()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentTracks(track: Track)

    @Query("SELECT * FROM tracks WHERE name LIKE :searchQuery ORDER BY searchedAt DESC")
    suspend fun findRecentTracksByName(searchQuery: String): List<Track>
}