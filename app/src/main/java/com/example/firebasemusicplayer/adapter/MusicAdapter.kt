package com.example.firebasemusicplayer.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.model.Music
//import com.squareup.picasso.Picasso

class MusicAdapter(musicList: List<Music>?) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {
    private val musicList: List<Music>?
    private var onItemClickListener: OnItemClickListener? = null

    init {
        this.musicList = musicList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_a, parent, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val music: Music = musicList!![position]
        holder.tv_id.text = "id: " + music.id
        holder.tv_songName.text = "Bài hát : " + music.songName
        holder.tv_singerName.text = "Ca sĩ : " + music.singerName
        holder.tv_imageURL.text = "image URL: " + music.imageURL
        holder.tv_songURL.text = "song URL: " + music.songURL

        /** Sử dụng thư viện Picasso để hiển thị hình ảnh **/
//        Picasso.with(holder.imageView.context).load(musicList[position].getImage())
//            .into(holder.imageView)

        /** sử dụng thư viện Glide để hiển thị hình ảnh **/
        Glide.with(holder.imageView.context).load(musicList.get(position).imageURL).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return musicList?.size ?: 0
    }

    inner class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_id: TextView
        val tv_songName: TextView
        val tv_singerName: TextView
        val tv_imageURL: TextView
        val tv_songURL: TextView
        val imageView : ImageView


        init {
            tv_id = itemView.findViewById(R.id.tv_id)
            tv_songName = itemView.findViewById(R.id.tv_songName)
            tv_singerName = itemView.findViewById(R.id.tv_singerName)
            tv_imageURL = itemView.findViewById(R.id.tv_imageURL)
            tv_songURL = itemView.findViewById(R.id.tv_songURL)
            imageView = itemView.findViewById(R.id.imageView)

            itemView.setOnClickListener {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(adapterPosition)
                }
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
//        fun songData(musicList: List<Music>?)
    }
}
