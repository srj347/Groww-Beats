package com.example.growwbeats.data.entities

import androidx.room.Entity
import java.io.Serializable
import java.util.Date

@Entity(tableName = "tracks", primaryKeys = ["id"])
data class Track(
    val id: String,
    val name: String,
    val album: Album,
    val external_urls: ExternalUrl,
    val duration_ms: String,
    val artists: List<Artist>,
    var searchedAt: Date
): ResponseResult()