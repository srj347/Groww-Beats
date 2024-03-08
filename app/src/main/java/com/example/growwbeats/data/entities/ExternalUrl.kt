package com.example.growwbeats.data.entities

import com.google.gson.annotations.SerializedName

data class ExternalUrl(
    @SerializedName("spotify")
    val deepLink: String
)
