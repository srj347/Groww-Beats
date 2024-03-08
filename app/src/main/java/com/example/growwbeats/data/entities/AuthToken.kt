package com.example.growwbeats.data.entities

data class AuthToken(
    val access_token: String,
    val token_type: String,
    val expires_in: String,
    var retrievedAt: String
)
