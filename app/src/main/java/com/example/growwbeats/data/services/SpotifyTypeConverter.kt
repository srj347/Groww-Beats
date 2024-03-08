package com.example.growwbeats.data.services

import androidx.room.TypeConverter
import com.example.growwbeats.data.entities.Album
import com.example.growwbeats.data.entities.Artist
import com.example.growwbeats.data.entities.ExternalUrl
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class SpotifyTypeConverter {
    @TypeConverter
    fun fromAlbum(album: Album): String {
        return Gson().toJson(album)
    }

    @TypeConverter
    fun toAlbum(albumJson: String): Album {
        val type = object : TypeToken<Album>() {}.type
        return Gson().fromJson(albumJson, type)
    }
    @TypeConverter
    fun fromExternalUrl(externalUrl: ExternalUrl): String {
        return Gson().toJson(externalUrl)
    }

    @TypeConverter
    fun toExternalUrl(externalUrlJson: String): ExternalUrl {
        return Gson().fromJson(externalUrlJson, ExternalUrl::class.java)
    }

    @TypeConverter
    fun fromArtistList(artistList: List<Artist>): String {
        return Gson().toJson(artistList)
    }

    @TypeConverter
    fun toArtistList(artistListJson: String): List<Artist> {
        val type = object : TypeToken<List<Artist>>() {}.type
        return Gson().fromJson(artistListJson, type)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millis: Long?): Date? {
        return millis?.let { Date(it) }
    }
}