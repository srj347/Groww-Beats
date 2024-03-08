package com.example.growwbeats.data.services

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SpotifyMusicClient {
    private const val BASE_URL = "https://api.spotify.com/v1/"

    fun getRetrofit(context: Context): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor{ chain ->
                val tokenManager = TokenManager(context)
                val token = tokenManager.getToken()

                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${token}")
                    .build()
                chain.proceed(newRequest)
            }
            .build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}