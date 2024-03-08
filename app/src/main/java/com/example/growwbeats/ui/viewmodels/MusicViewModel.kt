package com.example.growwbeats.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.growwbeats.data.entities.Track
import com.example.growwbeats.data.repositories.MusicRepository
import com.example.growwbeats.data.repositories.RecentSearchRepository
import com.example.growwbeats.data.repositories.Repository
import com.example.growwbeats.data.services.TokenManager
import com.example.growwbeats.domain.mappers.GetSearchUiStateUseCase
import com.example.growwbeats.ui.uistates.SearchUiState
import kotlinx.coroutines.launch

class MusicViewModel(
    private val localRepository: RecentSearchRepository,
    private val remoteRepository: MusicRepository,
    private val tokenManger: TokenManager
): ViewModel() {

    private val TAG = MusicViewModel::class.simpleName
    private val _searchUiState: MutableLiveData<SearchUiState> = MutableLiveData()
    val searchUiState: LiveData<SearchUiState> = _searchUiState

    fun getSearchResult(query: String, type: String){
        viewModelScope.launch {
            try{
                _searchUiState.value = SearchUiState.Loading
                val response = remoteRepository.getSearchResult(query, type)
                Log.d(TAG, response.toString())
                _searchUiState.value = GetSearchUiStateUseCase.convert(response)
            } catch (e: Exception){
                _searchUiState.value = SearchUiState.Error(e.message.toString())
                e.printStackTrace()
            }
        }
    }

    /**
     * Storing only "10 MOST RECENT TRACK", can be configured based on the flag
     */
    fun insertRecentTrack(track: Track){
        viewModelScope.launch {
            try {
                localRepository.insertRecentTrack(track)
            } catch (e: Exception){
                Log.d(TAG, "Error in inserting recent searched record: ${e.message.toString()}")
                e.printStackTrace()
            }
        }
    }

    fun getRecentTracks(){
        viewModelScope.launch {
            try {
                _searchUiState.value = SearchUiState.Loading
                val recentTracks = localRepository.fetchRecentSearchResult()
                Log.d(TAG, recentTracks.toString())
                _searchUiState.value = SearchUiState.Success(recentSearches = recentTracks)
            } catch (e: Exception){
                val error = e.message.toString()
                _searchUiState.value = SearchUiState.Error(error)
                Log.d(TAG, "Error in fetching recent searched record: ${error}")
                e.printStackTrace()
            }
        }
    }

    fun searchRecentTracksByName(searchQuery: String) {
        viewModelScope.launch {
            try {
                _searchUiState.value = SearchUiState.Loading
                val searchResults = localRepository.findRecentTracksByName(searchQuery)
                Log.d(TAG, searchResults.toString())
                _searchUiState.value = SearchUiState.Success(recentSearches = searchResults)
            } catch (e: Exception){
                val error = e.message.toString()
                _searchUiState.value = SearchUiState.Error(error)
                Log.d(TAG, "Error in fetching recent searched record: ${error}")
                e.printStackTrace()
            }
        }
    }

}