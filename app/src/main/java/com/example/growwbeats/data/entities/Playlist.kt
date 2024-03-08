package com.example.growwbeats.data.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Playlist(
    val id: String,
    val name: String,
    val external_urls: ExternalUrl,
    @SerializedName("images")
    val imageUrl: List<ImageUrl>
): ResponseResult()