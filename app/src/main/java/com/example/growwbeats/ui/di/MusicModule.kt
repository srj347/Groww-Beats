package com.example.growwbeats.ui.di

import com.example.growwbeats.data.repositories.MusicRepository
import com.example.growwbeats.data.repositories.RecentSearchRepository
import com.example.growwbeats.data.services.SpotifyAuthClient
import com.example.growwbeats.data.services.SpotifyMusicClient
import com.example.growwbeats.data.services.SpotifyMusicService
import com.example.growwbeats.data.services.TokenManager
import com.example.growwbeats.data.sources.LocalSpotifyDataSource
import com.example.growwbeats.data.sources.RemoteSpotifyDataSource
import com.example.growwbeats.ui.viewmodels.MusicViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val musicModule = module {
    single{
        SpotifyMusicClient.getRetrofit(androidContext())
            .create(SpotifyMusicService::class.java)
    }
    single{
        SpotifyAuthClient.spotifyAuthService
    }
    single{
        TokenManager(androidContext())
    }
    single{
        RemoteSpotifyDataSource(get(), get())
    }
    single{
        LocalSpotifyDataSource(get())
    }
    factory {
        MusicRepository(get())
    }
    factory {
        RecentSearchRepository(get())
    }
    viewModel {
        MusicViewModel(get(), get(), get())
    }
}