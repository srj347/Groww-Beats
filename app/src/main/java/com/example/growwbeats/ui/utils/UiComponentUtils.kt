package com.example.growwbeats.ui.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object UiComponentUtils {

    fun loadImageFromUrl(
        context: Context,
        imageUrl: String?,
        imageContainer: ImageView,
        defaultImageId: Int
    ){
        if(imageUrl.isNullOrEmpty()){
            imageContainer.setImageResource(defaultImageId)
        } else {
            Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .centerCrop()
                .into(imageContainer)
        }
    }

    fun hideSoftKeyboard(view : View, activity: Activity) {
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun View.gone(){
        visibility = View.GONE
    }

    fun View.visible(){
        visibility = View.VISIBLE
    }
}