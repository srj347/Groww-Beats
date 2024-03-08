package com.example.growwbeats

import android.app.Application
import android.util.Log
import com.example.growwbeats.data.services.SpotifyAuthService
import com.example.growwbeats.data.services.TokenManager
import com.example.growwbeats.ui.di.musicModule
import com.example.growwbeats.ui.di.roomDbModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApplication : Application() {

    private val TAG = BaseApplication::class.simpleName

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@BaseApplication)
            modules(listOf(musicModule, roomDbModule))
        }

        val spotifyAuthService: SpotifyAuthService = get()
        val tokenManager: TokenManager = get()

        refreshAuthToken(spotifyAuthService, tokenManager)
    }

    private fun refreshAuthToken(
        spotifyAuthService: SpotifyAuthService,
        tokenManager: TokenManager
    ) {
        if (tokenManager.isTokenExpired()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val tokenResponse = spotifyAuthService.getAuthToken()
                    tokenManager.saveToken(tokenResponse)
                    Log.d(TAG, "Token refreshed: ${tokenResponse.accessToken}")
                } catch (e: Exception) {
                    Log.d(TAG, "Failed to refresh the token: ${e.message}")
                }
            }
        }
    }
}