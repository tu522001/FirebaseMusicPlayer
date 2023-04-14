package com.example.firebasemusicplayer.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.model.entity.ToolsAdmin

class ToolsAdminAdapter(listToolsAdmin : List<ToolsAdmin>?) : RecyclerView.Adapter<ToolsAdminAdapter.ToolsAdminViewHolder>() {

    private val listToolsAdmin : List<ToolsAdmin>?
    private var onItemClickListener: OnItemClickListener? = null

    init {
        this.listToolsAdmin = listToolsAdmin
    }

    inner class ToolsAdminViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imageView: ImageView
        private var tvToolsAdmin: TextView


        init {
            imageView = itemView.findViewById(R.id.imgViewToolsAdmin)
            tvToolsAdmin = itemView.findViewById(R.id.txtViewToolsAdmin)

            itemView.setOnClickListener {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(adapterPosition)
                }
            }
        }

        fun bind(tools: ToolsAdmin) {
            imageView.setImageResource(tools.image)
            tvToolsAdmin.text = tools.text
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolsAdminViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_tools_admin, parent, false)
        return ToolsAdminViewHolder(view)
    }


    override fun onBindViewHolder(holder: ToolsAdminViewHolder, position: Int) {
        holder.bind(listToolsAdmin!![position])
    }

    override fun getItemCount(): Int {
        return listToolsAdmin?.size ?: 0
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
    }
}