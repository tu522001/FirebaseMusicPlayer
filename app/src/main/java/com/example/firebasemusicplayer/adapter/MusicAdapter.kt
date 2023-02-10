package com.example.firebasemusicplayer.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.model.Music
import com.squareup.picasso.Picasso


//class MusicAdapter(musicList: List<Music>?) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {
//    private val musicLists: List<Music>?
//    private var onItemClickListener: OnItemClickListener? = null
//
//    init {
//        this.musicLists = musicList
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
////        val view: View = LayoutInflater.from(parent.context).inflate(, parent, false)
//        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_a, parent,false)
//        return MusicViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
//        val music: Music = musicLists!![position] ?: return
//        holder.tv_maSV.text = "MaSV: " + sinhVien.getMaSV()
//        holder.tv_hoten.text = "Hoten: " + sinhVien.getHoten()
//        holder.tv_email.text = "Email: " + sinhVien.getEmail()
//        holder.tv_gioitinh.text = "GioiTinh: " + sinhVien.getGioitinh()
//        holder.tv_diem.text = "Diem: " + sinhVien.getDiem()
//        //        holder.imageView.setImageResource(Integer.parseInt("Image: "+sinhVien.getImage()));
//        Picasso.with(holder.imageView.context).load(sinhVienList[position].getImage())
//            .into(holder.imageView)
//    }
//
//    override fun getItemCount(): Int {
//        return musicLists?.size ?: 0
//    }
//
//    inner class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val tv_maSV: TextView
//        private val tv_hoten: TextView
//        private val tv_email: TextView
//        private val tv_gioitinh: TextView
//        private val tv_diem: TextView
//        private val imageView: ImageView
//
//        init {
//            tv_maSV = itemView.findViewById()
//            tv_hoten = itemView.findViewById(R.id.tv_hoten)
//            tv_email = itemView.findViewById(R.id.tv_email)
//            tv_gioitinh = itemView.findViewById(R.id.tv_gioiTinh)
//            tv_diem = itemView.findViewById(R.id.tv_diem)
//            imageView = itemView.findViewById(R.id.imageView)
//            itemView.setOnClickListener {
//                if (onItemClickListener != null) {
//                    onItemClickListener!!.onClick(adapterPosition)
//                }
//            }
//        }
//    }
//
//    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
//        this.onItemClickListener = onItemClickListener
//    }
//
//    interface OnItemClickListener {
//        fun onClick(position: Int)
//    }
//}

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
        val music: Music = musicList!![position] ?: return
        holder.tv_id.text = "id: " + music.id
        holder.tv_songName.text = "songName: " + music.songName
        holder.tv_singerName.text = "singerName: " + music.singerName
        holder.tv_imageURL.text = "imageURL: " + music.imageURL
        holder.tv_songURL.text = "songURL: " + music.songURL
        //        holder.imageView.setImageResource(Integer.parseInt("Image: "+sinhVien.getImage()));
//        Picasso.with(holder.imageView.context).load(musicList[position].getImage())
//            .into(holder.imageView)
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


        init {
            tv_id = itemView.findViewById(R.id.tv_id)
            tv_songName = itemView.findViewById(R.id.tv_songName)
            tv_singerName = itemView.findViewById(R.id.tv_singerName)
            tv_imageURL = itemView.findViewById(R.id.tv_imageURL)
            tv_songURL = itemView.findViewById(R.id.tv_songURL)

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
    }
}
