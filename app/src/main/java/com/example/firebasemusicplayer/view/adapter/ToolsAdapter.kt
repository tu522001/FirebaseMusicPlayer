package com.example.firebasemusicplayer.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.model.Music
import com.example.firebasemusicplayer.model.Tools

//class ToolsAdapter (var listTools: MutableList<Tools>) : RecyclerView.Adapter<ToolsAdapter.ToolsViewHolder>() {
//
//    private lateinit var onItemListener: OnItemListener
//
//    inner class ToolsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private var imageView: ImageView
//        private var tvSong: TextView
//
//        init {
//            imageView = itemView.findViewById(R.id.imgViewTools)
//            tvSong = itemView.findViewById(R.id.txtViewTools)
//        }
//
//        fun bind(tools: Tools) {
//            imageView.setImageResource(tools.image)
//            tvSong.text = tools.text
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolsViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val view = layoutInflater.inflate(R.layout.item_layout_tools, parent, false)
//        return ToolsViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ToolsViewHolder, position: Int) {
//        holder.bind(listTools[position])
//    }
//
//    override fun getItemCount(): Int {
//        return listTools.size
//    }
//
//    fun setOnItemClick(onItemListener: OnItemListener) {
//        this.onItemListener = onItemListener
//    }
//
//    interface OnItemListener {
//        fun onClick(position: Int)
//    }
//
//
//}

class ToolsAdapter(listTools: List<Tools>?) : RecyclerView.Adapter<ToolsAdapter.ToolsViewHolder>() {
    private val listTools: List<Tools>?
    private var onItemClickListener: OnItemClickListener? = null

    init {
        this.listTools = listTools
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolsViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_tools, parent, false)
        return ToolsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToolsViewHolder, position: Int) {
        holder.bind(listTools!![position])
    }

    override fun getItemCount(): Int {
        return listTools?.size ?: 0
    }

    inner class ToolsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imageView: ImageView
        private var tvSong: TextView


        init {
            imageView = itemView.findViewById(R.id.imgViewTools)
            tvSong = itemView.findViewById(R.id.txtViewTools)

            itemView.setOnClickListener {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(adapterPosition)
                }
            }
        }

        fun bind(tools: Tools) {
            imageView.setImageResource(tools.image)
            tvSong.text = tools.text
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
    }
}
