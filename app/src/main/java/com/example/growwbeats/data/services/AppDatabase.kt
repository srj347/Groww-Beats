package com.example.growwbeats.data.services

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.growwbeats.data.entities.Track

@Database(entities = [Track::class], version = 1)
@TypeConverters(SpotifyTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchDao
}