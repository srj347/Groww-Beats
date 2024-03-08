package com.example.growwbeats.data.services

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.growwbeats.data.entities.AuthTokenResponse

class TokenManager(context: Context) {
    private val preferences = context.getSharedPreferences("token_preferences", Context.MODE_PRIVATE)

    fun saveToken(authTokenResponse: AuthTokenResponse) {
        preferences.edit {
            putString("access_token", authTokenResponse.accessToken)
            putLong("expiration_time", authTokenResponse.expirationTimeMillis)
        }
    }

    fun getToken(): String? = preferences.getString("access_token", null)

    fun isTokenExpired(): Boolean {
        val expirationTime = preferences.getLong("expiration_time", 0)
        return System.currentTimeMillis() / 1000 >= expirationTime
    }
}
