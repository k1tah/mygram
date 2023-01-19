package com.example.mygram.presentation.activity.recyclerView.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mygram.databinding.ItemChatBinding
import com.example.mygram.domain.Chat
import com.example.mygram.presentation.activity.recyclerView.viewHolders.ChatHolder

class MainAdapter: ListAdapter<Chat, ChatHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        return ChatHolder(ItemChatBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        object DiffCallback : DiffUtil.ItemCallback<Chat>() {
            override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
                return false
            }

        }
    }

}