package com.example.growwbeats.data.entities

import com.google.gson.annotations.SerializedName

data class AuthTokenResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val expiresIn: Long
) {
    val expirationTimeMillis: Long
        get() = System.currentTimeMillis() / 1000 + expiresIn
}
