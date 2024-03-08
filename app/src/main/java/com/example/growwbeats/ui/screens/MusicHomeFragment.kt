package com.example.growwbeats.ui.screens

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.growwbeats.data.entities.Album
import com.example.growwbeats.data.entities.Artist
import com.example.growwbeats.data.entities.Playlist
import com.example.growwbeats.data.entities.ResponseResult
import com.example.growwbeats.data.entities.Track
import com.example.growwbeats.databinding.FragmentMusicHomeBinding
import com.example.growwbeats.ui.adapters.SearchAdapter
import com.example.growwbeats.ui.uistates.SearchUiState
import com.example.growwbeats.ui.utils.NetworkChangeReceiver
import com.example.growwbeats.ui.utils.SearchType
import com.example.growwbeats.ui.utils.SearchViewUtils
import com.example.growwbeats.ui.utils.UiComponentUtils
import com.example.growwbeats.ui.utils.UiComponentUtils.gone
import com.example.growwbeats.ui.utils.UiComponentUtils.visible
import com.example.growwbeats.ui.viewmodels.MusicViewModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Date

class MusicHomeFragment : Fragment() {

    private lateinit var networkChangeReceiver: NetworkChangeReceiver
    private var isNetworkAvailable: Boolean = true
    private val musicViewModel by viewModel<MusicViewModel>()
    private val navController by lazy { findNavController() }
    private val binding by lazy {
        FragmentMusicHomeBinding.inflate(layoutInflater)
    }
    private val searchViewUtils by lazy {
        SearchViewUtils(requireContext(), binding.compSearch)
    }

    private val coroutineScope = lifecycle.coroutineScope
    private var searchJob: Job? = null

    private var selectedSearchType = SearchType.TRACK.type
    private var searchQuery = ""
    private var isFromHome = true

    override fun onResume() {
        super.onResume()
        /**
         * Flag to retrieve the focus of search view
         * Manually open search view for the first time
         */
        if(!isFromHome){
            binding.compSearch.etSearch.requestFocus()
        }
        isFromHome = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNetworkChangeListener()
        performTaskBasedOnStateParameters(isNetworkAvailable, searchQuery, selectedSearchType)
        initViewListeners()
        observeStateUpdates()
    }

    private fun performTaskBasedOnStateParameters(isNetworkAvailable: Boolean, searchQuery: String, selectedSearchType: String){
        if(searchQuery.isNullOrEmpty()){
            // Enable access to recent search
            enableRecentSearch()
            fetchRecentSearchResult(searchQuery)
        } else {
            if(isNetworkAvailable){
                disableRecentSearch()
                fetchAPISearchResult(searchQuery)
            } else {
                enableRecentSearch()
                fetchRecentSearchResult(searchQuery)
            }
        }
    }

    private fun initViewListeners() {
        searchViewUtils.setOnFocusChangeListener()
        searchViewUtils.setSearchQueryChangeListener { searchQuery ->
            this.searchQuery = searchQuery
            performTaskBasedOnStateParameters(isNetworkAvailable, searchQuery, selectedSearchType)
        }
        binding.chipGroupSearch.setOnCheckedStateChangeListener { group, checkedIds ->
            if (!checkedIds.isNullOrEmpty()) {
                val selectedChip: Chip = group.findViewById(checkedIds[0])
                selectedSearchType = SearchViewUtils.searchTypeValuesMap[selectedChip.text.toString()].toString()
                performTaskBasedOnStateParameters(isNetworkAvailable, searchQuery, selectedSearchType)
                UiComponentUtils.hideSoftKeyboard(group, requireActivity())
            }
        }
        binding.rvSearch.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    // Scrolling up
                    UiComponentUtils.hideSoftKeyboard(recyclerView, requireActivity())
                } else {
                    // Scrolling down
                }
            }
        })
        binding.rvRecentSearch.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    // Scrolling up
                    UiComponentUtils.hideSoftKeyboard(recyclerView, requireActivity())
                } else {
                    // Scrolling down
                }
            }
        })
    }

    private fun fetchAPISearchResult(searchQuery: String) {
        if(searchQuery.isNullOrEmpty()) return
//          Don't perform search operation
//          if (searchQuery.length <= 2) return

        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            delay(500) // Debouncing effect
            musicViewModel.getSearchResult(searchQuery, selectedSearchType.lowercase())
        }
    }

    private fun fetchRecentSearchResult(searchQuery: String) {
        musicViewModel.searchRecentTracksByName(searchQuery)
    }

    private fun observeStateUpdates() {
        musicViewModel.searchUiState.observe(requireActivity()) { state ->
            if (state is SearchUiState.Success) {
                if (!state.albums.isNullOrEmpty()) {
                    setUpAPISearchAdapter(state.albums as ArrayList<Album>, SearchType.ALBUM.type) {
                        val album = it as Album
                        val direction = MusicHomeFragmentDirections.actionMusicHomeToMusicDetail(album?.external_urls?.deepLink.toString())
                        navController.navigate(direction)
                    }
                } else if (!state.artists.isNullOrEmpty()) {
                    setUpAPISearchAdapter(state.artists as ArrayList<Artist>, SearchType.ARTIST.type) {
                        val artist = it as Artist

                        val direction = MusicHomeFragmentDirections.actionMusicHomeToMusicDetail(artist?.external_urls?.deepLink.toString())
                        navController.navigate(direction)
                    }
                } else if (!state.playlists.isNullOrEmpty()) {
                    setUpAPISearchAdapter(
                        state.playlists as ArrayList<Playlist>,
                        SearchType.PLAYLIST.type
                    ) {
                        val playlist = it as Playlist

                        val direction = MusicHomeFragmentDirections.actionMusicHomeToMusicDetail(playlist?.external_urls?.deepLink.toString())
                        navController.navigate(direction)
                    }
                } else if (!state.tracks.isNullOrEmpty()) {
                    setUpAPISearchAdapter(state.tracks as ArrayList<Track>, SearchType.TRACK.type) {
                        val track = it as Track
                        track.searchedAt = Date()
                        musicViewModel.insertRecentTrack(it)

                        val direction = MusicHomeFragmentDirections.actionMusicHomeToMusicDetail(track?.album?.external_urls?.deepLink.toString())
                        navController.navigate(direction)
                    }
                } else if(!state.recentSearches.isNullOrEmpty()) {
                    setUpRecentSearchAdapter(state.recentSearches as ArrayList<Track>){
                        val track = (it as Track).copy(searchedAt = Date())
                        musicViewModel.insertRecentTrack(track)

                        val direction = MusicHomeFragmentDirections.actionMusicHomeToMusicDetail(track?.album?.external_urls?.deepLink.toString())
                        navController.navigate(direction)
                    }
                }
            }
        }
    }

    private fun <T> setUpAPISearchAdapter(
        data: ArrayList<T>?,
        searchType: String,
        onItemClick: (data: Any) -> Unit
    ) {
        val searchAdapter = SearchAdapter(requireActivity(), data!!, searchType, onItemClick)
        binding.rvSearch.apply {
            layoutManager = if(searchType == SearchType.ALBUM.type) {
                GridLayoutManager(requireActivity(), 2)
            } else {
                LinearLayoutManager(requireActivity())
            }
            adapter = searchAdapter
        }
        searchAdapter.notifyDataSetChanged()
    }

    private fun <T> setUpRecentSearchAdapter(
        data: ArrayList<T>?,
        onItemClick: (data: Any) -> Unit
    ) {
        val searchAdapter = SearchAdapter(requireActivity(), data!!, SearchType.TRACK.type, onItemClick)
        binding.rvRecentSearch.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = searchAdapter
        }
        searchAdapter.notifyDataSetChanged()
    }

    private fun enableRecentSearch(){
        binding.rvSearch.gone()
        binding.chipGroupSearch.gone()
        binding.rvRecentSearch.visible()
        binding.tvRecentSearches.visible()
    }

    private fun disableRecentSearch(){
        binding.rvSearch.visible()
        binding.chipGroupSearch.visible()
        binding.rvRecentSearch.gone()
        binding.tvRecentSearches.gone()
    }

    private fun setNetworkChangeListener() {
        // Registering broadcast receiver for network change
        networkChangeReceiver = NetworkChangeReceiver { isConnected ->
            isNetworkAvailable = isConnected
        }
        requireActivity().registerReceiver(
            networkChangeReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().unregisterReceiver(networkChangeReceiver)
    }
}