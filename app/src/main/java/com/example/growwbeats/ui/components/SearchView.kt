package com.example.growwbeats.ui.components

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.growwbeats.R
import com.example.growwbeats.databinding.CompSearchViewBinding

class SearchView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {

    private val TAG = SearchView::class.simpleName
    private lateinit var binding: CompSearchViewBinding

    fun initUI(onSearchQueryChangeListener: (newText: String) -> Unit) {
        binding = CompSearchViewBinding.inflate(LayoutInflater.from(context), this, false)
        initSearchListener(onSearchQueryChangeListener)
    }

    private fun initSearchListener(onSearchQueryChangeListener: (newText: String) -> Unit) {
        binding.etSearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onSearchQueryChangeListener(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }
}