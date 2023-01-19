package com.example.mygram.presentation.activity.recyclerView.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mygram.data.repository.UID
import com.example.mygram.databinding.ItemImageMessageBinding
import com.example.mygram.domain.Message
import com.example.mygram.utils.asTime
import com.example.mygram.utils.downloadAndSetImage

class ImageMessageViewHolder(private var binding: ItemImageMessageBinding) :
    RecyclerView.ViewHolder(binding.root), MessageHolder {
    override fun bind(message: Message) {
        binding.apply {
            if (message.from == UID) {
                imageUserContainer.visibility = View.VISIBLE
                messageImageUser.downloadAndSetImage(message.url)
                imageUserTime.text = message.timestamp.asTime()
            } else {
                imageAnswerContainer.visibility = View.VISIBLE
                messageImageAnswer.downloadAndSetImage(message.url)
                imageAnswerTime.text = message.timestamp.asTime()
            }
        }
    }

}