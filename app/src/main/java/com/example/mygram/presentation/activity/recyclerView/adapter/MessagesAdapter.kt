package com.example.mygram.presentation.activity.recyclerView.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mygram.data.repository.TYPE_PHOTO
import com.example.mygram.data.repository.VIEW_IMAGE
import com.example.mygram.data.repository.VIEW_TEXT
import com.example.mygram.domain.Message
import com.example.mygram.presentation.activity.recyclerView.viewHolders.HolderFactory
import com.example.mygram.presentation.activity.recyclerView.viewHolders.MessageHolder


class MessagesAdapter : ListAdapter<Message, RecyclerView.ViewHolder>(DiffCallback) {
    private lateinit var item: Message
    override fun getItemViewType(position: Int): Int {
        item = getItem(position)
        return when (item.type) {
            TYPE_PHOTO -> VIEW_IMAGE
            else -> VIEW_TEXT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HolderFactory.getHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MessageHolder).bind(item)
    }

    companion object {
        object DiffCallback : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
                return false
            }

        }
    }


}