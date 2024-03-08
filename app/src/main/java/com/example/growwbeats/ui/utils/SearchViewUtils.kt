package com.example.growwbeats.ui.utils

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import androidx.lifecycle.Lifecycle
import com.example.growwbeats.databinding.CompSearchViewBinding
import com.example.growwbeats.ui.utils.UiComponentUtils.gone
import com.example.growwbeats.ui.utils.UiComponentUtils.visible
import kotlin.reflect.KClass

class SearchViewUtils(
    val context: Context,
    val compSearchBinding: CompSearchViewBinding
) {

    companion object {
        val searchTypeValuesMap by lazy {
            mapOf(
                "Albums" to SearchType.ALBUM.type,
                "Artists" to SearchType.ARTIST.type,
                "Tracks" to SearchType.TRACK.type,
                "Playlists" to SearchType.PLAYLIST.type
            )
        }
    }

    fun setOnFocusChangeListener(){
        val searchTv = compSearchBinding.tvSearch
        val searchEt = compSearchBinding.etSearch

        searchTv.setOnClickListener{
            compSearchBinding.tvSearch.visibility = View.GONE
            compSearchBinding.etSearch.gravity = Gravity.START
        }

        searchEt.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                compSearchBinding.tvSearch.visibility = View.GONE
                compSearchBinding.etSearch.gravity = Gravity.START
                compSearchBinding.icBack.visible()
            } else {
                compSearchBinding.tvSearch.visibility = View.VISIBLE
                compSearchBinding.etSearch.gravity = Gravity.CENTER
                compSearchBinding.etSearch.clearFocus()
                compSearchBinding.icBack.gone()
            }
        }

        compSearchBinding.icBack.setOnClickListener {
            compSearchBinding.etSearch.clearFocus()
            compSearchBinding.etSearch.text.clear()
            UiComponentUtils.hideSoftKeyboard(compSearchBinding.etSearch, context as Activity)
        }

        compSearchBinding.icClear.setOnClickListener {
            compSearchBinding.etSearch.text.clear()
            compSearchBinding.etSearch.requestFocus()
        }
    }

    fun setSearchQueryChangeListener(onSearchTextChange: (searchQuery: String) -> Unit) {
        compSearchBinding.etSearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onSearchTextChange(s.toString())
                val newText = s.toString()
                if(newText.isEmpty()){
                    compSearchBinding.icClear.gone()
                } else {
                    compSearchBinding.icClear.visible()
                }
            }
            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
}