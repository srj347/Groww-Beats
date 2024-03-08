package com.example.growwbeats.ui.uistates

import com.example.growwbeats.data.entities.Album
import com.example.growwbeats.data.entities.Artist
import com.example.growwbeats.data.entities.Playlist
import com.example.growwbeats.data.entities.Track

sealed class SearchUiState {
    data object Loading : SearchUiState()
    data class Success(
        val albums: List<Album>? = null,
        val artists: List<Artist>? = null,
        val tracks: List<Track>? = null,
        val playlists: List<Playlist>? = null,
        val recentSearches: List<Track>? = null
    ) : SearchUiState()
    data class Error(val errorMessage: String) : SearchUiState()
}
