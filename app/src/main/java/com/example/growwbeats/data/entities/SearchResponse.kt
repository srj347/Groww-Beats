package com.example.growwbeats.data.entities

data class SearchResponse(
    val tracks: TrackResponse,
    val albums: AlbumResponse,
    val playlists: PlaylistResponse,
    val artists: ArtistResponse
)