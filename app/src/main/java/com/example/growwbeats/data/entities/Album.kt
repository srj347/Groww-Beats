package com.example.growwbeats.data.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Album(
    val id: String,
    val name: String,
    val artists: List<Artist>,
    val release_date: String,
    val album_type: String,
    val external_urls: ExternalUrl,
    val images: List<ImageUrl>,
): ResponseResult()
