package com.example.mygram.presentation.activity.recyclerView.viewHolders

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mygram.R
import com.example.mygram.data.repository.TYPE_PHOTO
import com.example.mygram.data.repository.TYPE_TEXT
import com.example.mygram.databinding.ItemChatBinding
import com.example.mygram.domain.Chat
import com.example.mygram.presentation.activity.fragments.MainFragmentDirections
import com.example.mygram.utils.downloadAndSetImage

class ChatHolder(private var binding: ItemChatBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(chat: Chat){
        binding.apply {
            if (chat.messageType == TYPE_TEXT){
                lastMessage.text = chat.lastMessage
            } else if (chat.messageType == TYPE_PHOTO) {
                lastMessage.setText(R.string.image)
            }
            if (chat.isChecked == 0){
                isChecked.setImageResource(R.drawable.ic_baseline_circle_24)
            }
            chatContainer.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToSingleChatFragment(chat.id)
                it.findNavController().navigate(action)
            }
            chatName.text = chat.name
            chatImage.downloadAndSetImage(chat.imageUrl)
        }
    }
}