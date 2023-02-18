package com.example.probleminternship

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class AppDataAdapter : ListAdapter<App, RecyclerView.ViewHolder>(AppDiffCallback()) {
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item: Pair<String, Drawable?> = Pair(getItem(position).name, getItem(position).icon)
        if (holder is MyViewHolder) {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder.from(parent)
    }

    class MyViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.iconApp)
        private val nameApp: TextView = itemView.findViewById(R.id.nameApp)

        fun bind(item: Pair<String, Drawable?>) {
            nameApp.text = item.first
            icon.setImageDrawable(item.second)
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.list_item_app_info, parent, false)

                return MyViewHolder(view)
            }
        }
    }

    class AppDiffCallback : DiffUtil.ItemCallback<App>() {

        override fun areItemsTheSame(oldItem: App, newItem: App): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: App, newItem: App): Boolean {
            return oldItem == newItem
        }
    }
}