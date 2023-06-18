package com.example.firebasemusicplayer.view.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebasemusicplayer.view.listeners.OnClickListenerPlayMusic
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.ItemLayoutSongBinding
import com.example.firebasemusicplayer.model.entity.Music
class MusicAdapter(var context: Context, var onClickListenerPlayMusic: OnClickListenerPlayMusic, var musicList: List<Music>) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            var itemBinding = ItemLayoutSongBinding.inflate(layoutInflater, parent, false)
        return MusicViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.bind(musicList[position])
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    inner class MusicViewHolder(var itemBinding: ItemLayoutSongBinding ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(music: Music) {
            itemBinding.tvId.text = music.id.toString()
            itemBinding.tvSongName.text = music.songName
            itemBinding.tvSingerName.text = music.singerName

            when(music.id){
                1-> {
                    itemBinding.tvId.setTextColor(ContextCompat.getColor(context,R.color.red))
                }
                2->{
                    itemBinding.tvId.setTextColor(ContextCompat.getColor(context,R.color.green))
                }
                3->{
                    itemBinding.tvId.setTextColor(ContextCompat.getColor(context,R.color.turquoise))
                }
                else->{
                    itemBinding.tvId.setTextColor(ContextCompat.getColor(context,R.color.black))
                }
            }
            Glide.with(itemBinding.imgSong.context).load(music.imageURL).into(itemBinding.imgSong)
                itemBinding.itemLayout.setOnClickListener{
                    onClickListenerPlayMusic.onClickPlayMusic(position)
                }

        }
    }

}
