package com.example.growwbeats.data.services

import com.example.growwbeats.data.entities.AuthTokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface SpotifyAuthService {
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("api/token")
    suspend fun getAuthToken(
        @Field("grant_type") grantType: String = "client_credentials",
        @Field("client_id") clientId: String = SpotifyAuthClient.CLIENT_ID,
        @Field("client_secret") clientSecret: String = SpotifyAuthClient.CLIENT_SECRET_KEY
    ): AuthTokenResponse
}