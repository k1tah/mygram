package com.example.mygram.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mygram.databinding.MessageItemBinding
import com.example.mygram.domain.Message

/*
class MessagesAdapter(): ListAdapter<Message, MessagesAdapter.MessageViewHolder>(DiffCalback) {
    class MessageViewHolder(private var binnding: MessageItemBinding): RecyclerView.ViewHolder(binnding.root){
        fun bind(message: Message){
            binnding.messageText.text = message.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            MessageItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind()
    }

    companion object DiffCalback = object : DiffUtil.ItemCallback<Message>(){
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return  oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

    }



}*/