package com.example.firebasemusicplayer.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.model.entity.Singer

class SingerAdapter (singerList: List<Singer>?) : RecyclerView.Adapter<SingerAdapter.SingerViewHolder>() {

    private val singerList : List<Singer>?

    init {
        this.singerList = singerList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingerViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_singer, parent, false)
        return SingerViewHolder(view)
    }

    override fun onBindViewHolder(holder: SingerViewHolder, position: Int) {
        val singer : Singer = singerList!![position]
        holder.tv_singerName_b.text = singer.singerName
        Glide.with(holder.img_singer_b.context).load(singerList.get(position).imageURL).into(holder.img_singer_b)

    }

    override fun getItemCount(): Int {
        return singerList?.size ?: 0
    }


    inner class SingerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img_singer_b : ImageView
        val tv_singerName_b : TextView

        init {
            img_singer_b = itemView.findViewById(R.id.img_singer_b);
            tv_singerName_b = itemView.findViewById(R.id.tv_singerName_b)
        }
    }

}