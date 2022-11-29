package com.example.mygram.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mygram.databinding.MessageItemBinding
import com.example.mygram.domain.Message
import com.example.mygram.utils.TYPE_PHOTO
import com.example.mygram.utils.UID
import com.example.mygram.utils.asTime
import com.example.mygram.utils.downloadAndSetImage


class MessagesAdapter: ListAdapter<Message, MessagesAdapter.MessageViewHolder>(DiffCallback) {
    class MessageViewHolder(private var binding: MessageItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(message: Message){
            binding.apply {
                if (message.type == TYPE_PHOTO){
                    if (message.from == UID){
                        imageUserContainer.visibility = View.VISIBLE
                        messageImageUser.downloadAndSetImage(message.photoUrl)
                        messageUserContainer.visibility = View.VISIBLE
                        messageAnswerContainer.visibility = View.GONE
                        messageTextUser.text = message.text
                        messageUserTime.text = message.timestamp.asTime()
                    }
                    else {
                        imageAnswerContainer.visibility = View.VISIBLE
                        messageImageAnswer.downloadAndSetImage(message.photoUrl)
                        messageAnswerContainer.visibility = View.VISIBLE
                        messageUserContainer.visibility = View.GONE
                        messageTextAnswer.text = message.text
                        messageAnswerTime.text = message.timestamp.asTime()
                    }
                }
                else {
                    if (message.from == UID){
                        messageUserContainer.visibility = View.VISIBLE
                        messageAnswerContainer.visibility = View.GONE
                        messageTextUser.text = message.text
                        messageUserTime.text = message.timestamp.asTime()
                    }
                    else {
                        messageAnswerContainer.visibility = View.VISIBLE
                        messageUserContainer.visibility = View.GONE
                        messageTextAnswer.text = message.text
                        messageAnswerTime.text = message.timestamp.asTime()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            MessageItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        object DiffCallback: DiffUtil.ItemCallback<Message>(){
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
                return  oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
                return false
            }

        }
    }



}