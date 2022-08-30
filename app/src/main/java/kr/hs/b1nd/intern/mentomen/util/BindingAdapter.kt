package kr.hs.b1nd.intern.mentomen.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("image")
    fun loadImage(view: ImageView, imageUrl: String) {
        Glide.with(view.context)
            .load(imageUrl)
            .into(view)
    }
}