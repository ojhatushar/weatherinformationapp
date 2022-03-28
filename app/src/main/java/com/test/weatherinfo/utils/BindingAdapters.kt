package com.test.weatherinfo.utils


import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.test.weatherinfo.R


@BindingAdapter("setImageUrl")
fun ImageView.setImageUrl(imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(context).load("http://openweathermap.org/img/w/$imageUrl.png").placeholder(R.mipmap.ic_launcher).into(this)
    }
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}



/*@BindingAdapter("imageUrl")
fun ImageView.bindImage(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(if (imgUrl.contains(NO_IMG_FILE_NAME)) R.drawable.broken_image else imgUri)
            .fitCenter()
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.broken_image)
            )
            .into(this)
    }
}*/

