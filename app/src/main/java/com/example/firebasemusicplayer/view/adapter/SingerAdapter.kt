package com.example.firebasemusicplayer.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebasemusicplayer.databinding.ItemLayoutSingerBinding
import com.example.firebasemusicplayer.model.entity.Singer
import com.example.firebasemusicplayer.view.listeners.OnClickListenerPlayMusic
import com.example.firebasemusicplayer.view.listeners.OnClickSingerInformation

class SingerAdapter (var context : Context, var onClickSingerInformation: OnClickSingerInformation, var singerList: List<Singer>) : RecyclerView.Adapter<SingerAdapter.SingerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingerViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemLayoutSingerBinding.inflate(layoutInflater, parent, false)
        return SingerViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SingerViewHolder, position: Int) {
        holder.bind(singerList[position])
    }

    override fun getItemCount(): Int {
        return singerList.size
    }


    inner class SingerViewHolder(var itemBinding: ItemLayoutSingerBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(singer: Singer) {
            itemBinding.tvSingerNameB.text = singer.singerName
            Glide.with(itemBinding.imgSingerB.context).load(singer.imageURL).into(itemBinding.imgSingerB)

            itemBinding.itemLayout.setOnClickListener{
                onClickSingerInformation.onClickSingerInformation(position)
                Log.d("RRR","Data : singer : "+singer.data+" , height : "+singer.height+" , placeOfBirth : "+singer.placeOfBirth+" , sex : "+singer.sex+" , yearOfOperation : "+singer.singerName)
            }

        }
    }
}