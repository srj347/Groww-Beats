package com.example.growwbeats.ui.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.SearchView
import androidx.navigation.fragment.navArgs
import com.example.growwbeats.R
import com.example.growwbeats.data.entities.Track
import com.example.growwbeats.databinding.FragmentMusicDetailBinding
import com.example.growwbeats.ui.utils.SearchType
import com.example.growwbeats.ui.utils.SearchViewUtils
import com.example.growwbeats.ui.utils.UiComponentUtils.gone
import com.example.growwbeats.ui.utils.UiComponentUtils.visible

class MusicDetailFragment : Fragment() {

    val binding by lazy {
        FragmentMusicDetailBinding.inflate(layoutInflater)
    }

    private val args: MusicDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun initUI() {
        if(args.KEYURL.isNullOrEmpty()){
            binding.tvError.visible()
            return
        }
        val hostView = binding.wvHost
        hostView.apply {
            loadUrl(args.KEYURL)
            webViewClient = object: WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
//                    binding.pbLoader.visible()
//                    binding.pbLoader.playAnimation()
                    binding.wvHost.gone()
                    binding.tvError.gone()
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
//                    binding.pbLoader.pauseAnimation()
//                    binding.pbLoader.gone()
                    binding.tvError.gone()
                    binding.wvHost.visible()
                }

                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?
                ) {
                    super.onReceivedError(view, errorCode, description, failingUrl)
                    binding.tvError.visible()
                }
            }
        }
    }

}