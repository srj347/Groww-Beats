package com.example.growwbeats.domain.mappers

import com.example.growwbeats.data.entities.SearchResponse
import com.example.growwbeats.ui.uistates.SearchUiState

object GetSearchUiStateUseCase {
    fun convert(searchResponse: SearchResponse?): SearchUiState {
        return try {
            SearchUiState.Success(
                albums = searchResponse?.albums?.items,
                artists = searchResponse?.artists?.items,
                tracks = searchResponse?.tracks?.items,
                playlists = searchResponse?.playlists?.items
            )
        } catch (e: Exception) {
            SearchUiState.Error("Error while mapping: ${e.message}")
        }
    }
}