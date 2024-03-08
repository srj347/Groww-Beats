package com.example.growwbeats.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.growwbeats.R
import com.example.growwbeats.data.entities.Album
import com.example.growwbeats.data.entities.Artist
import com.example.growwbeats.data.entities.Playlist
import com.example.growwbeats.data.entities.Track
import com.example.growwbeats.databinding.ItemviewSearchAlbumsBinding
import com.example.growwbeats.databinding.ItemviewSearchArtistBinding
import com.example.growwbeats.databinding.ItemviewSearchPlaylistBinding
import com.example.growwbeats.databinding.ItemviewSearchTracksBinding
import com.example.growwbeats.ui.utils.SearchType
import com.example.growwbeats.ui.utils.UiComponentUtils
import com.example.growwbeats.ui.utils.UiComponentUtils.gone

class SearchAdapter<T>(
    private val context: Context,
    private val list: ArrayList<T>,
    private val searchType: String,
    private val onItemClick: (data: Any) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        val viewHolder = when(searchType){
            SearchType.ALBUM.type -> AlbumViewHolder(ItemviewSearchAlbumsBinding.inflate(inflater, parent, false))
            SearchType.ARTIST.type -> ArtistViewHolder(ItemviewSearchTracksBinding.inflate(inflater, parent, false))
            SearchType.PLAYLIST.type -> PlaylistViewHolder(ItemviewSearchTracksBinding.inflate(inflater, parent, false))
            else -> TrackViewHolder(ItemviewSearchTracksBinding.inflate(inflater, parent, false))
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list[position]
        when (searchType) {
            SearchType.ALBUM.type -> (holder as SearchAdapter<*>.AlbumViewHolder).bind(data as Album)
            SearchType.ARTIST.type -> (holder as SearchAdapter<*>.ArtistViewHolder).bind(data as Artist)
            SearchType.PLAYLIST.type -> (holder as SearchAdapter<*>.PlaylistViewHolder).bind(data as Playlist)
            SearchType.TRACK.type -> (holder as SearchAdapter<*>.TrackViewHolder).bind(data as Track, onItemClick)
        }
    }

    inner class TrackViewHolder(private val binding: ItemviewSearchTracksBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track, onItemClick: (data: Any) -> Unit){
            binding.apply {
                binding.tvTrackName.text = track.name
                binding.tvTrackArtists.text = track.artists.map { it.name }.joinToString(", ")
                UiComponentUtils.loadImageFromUrl(context, track?.album?.images?.firstOrNull()?.url, binding.ivTrack, R.drawable.ic_launcher_background)
            }
            binding.root.setOnClickListener{
                onItemClick(track)
            }
        }
    }
    inner class AlbumViewHolder(private val binding: ItemviewSearchAlbumsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album){
            binding.apply {
                tvAlbumName.text = album.name
                tvAlbumRelease.text = album.release_date?.substring(0, 4)
                tvAlbumArtists.text = album.artists.map { it.name }.joinToString(", ")
                UiComponentUtils.loadImageFromUrl(context, album?.images?.firstOrNull()?.url, binding.ivAlbum, R.drawable.ic_launcher_background)
            }
            binding.root.setOnClickListener {
                onItemClick(album)
            }
        }
    }
    inner class PlaylistViewHolder(private val binding: ItemviewSearchTracksBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: Playlist){
            binding.apply {
                binding.tvTrackName.text = playlist.name
                binding.tvTrackArtists.gone()
                UiComponentUtils.loadImageFromUrl(context, playlist?.imageUrl?.firstOrNull()?.url ?: "", binding.ivTrack, R.drawable.ic_launcher_background)
            }
            binding.root.setOnClickListener {
                onItemClick(playlist)
            }
        }
    }
    inner class ArtistViewHolder(private val binding: ItemviewSearchTracksBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist){
            binding.apply {
                binding.tvTrackName.text = artist.name
                binding.tvTrackArtists.gone()
                UiComponentUtils.loadImageFromUrl(context, artist?.imageUrl?.firstOrNull()?.url ?: "", binding.ivTrack, R.drawable.ic_launcher_background)
            }
            binding.root.setOnClickListener {
                onItemClick(artist)
            }
        }
    }
}