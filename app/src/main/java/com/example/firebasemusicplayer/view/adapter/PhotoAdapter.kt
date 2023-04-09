package com.example.firebasemusicplayer.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.model.Photo

class PhotoAdapter(private val mContext: Context, mListPhoto: List<Photo>?) :
    PagerAdapter() {
    private val mListPhoto: List<Photo>?

    init {
        this.mListPhoto = mListPhoto
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View =
            LayoutInflater.from(container.context).inflate(R.layout.item_photo, container, false)
        val imgPhoto = view.findViewById<ImageView>(R.id.img_photo)
        val photo: Photo = mListPhoto!![position]
        if (photo != null) {
            Glide.with(mContext).load(photo.imageURL).into(imgPhoto)
        }

        // Add view to viewgroup
        container.addView(view)
        return view
    }

    override fun getCount(): Int {
        return mListPhoto?.size ?: 0
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // Remove view
        container.removeView(`object` as View)
    }
}
