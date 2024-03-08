package com.example.growwbeats.data.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SpotifyAuthClient {

    private const val BASE_URL = "https://accounts.spotify.com/"
    const val CLIENT_ID = "332effd55c54444aa4eadffc84b1969b"
    const val CLIENT_SECRET_KEY = "3ca60dd19295466b9197d48970f95591"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val spotifyAuthService: SpotifyAuthService = retrofit.create(SpotifyAuthService::class.java)
}