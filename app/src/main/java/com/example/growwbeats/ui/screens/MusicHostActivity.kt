package com.example.growwbeats.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.growwbeats.R
import com.example.growwbeats.ui.viewmodels.MusicViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MusicHostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_host)
    }

}