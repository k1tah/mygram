package com.example.mygram.presentation.activity.recyclerView.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mygram.data.repository.UID
import com.example.mygram.databinding.ItemTextMessageBinding
import com.example.mygram.domain.Message
import com.example.mygram.utils.asTime

class TextMessageViewHolder(private var binding: ItemTextMessageBinding) :
    RecyclerView.ViewHolder(binding.root), MessageHolder {
    override fun bind(message: Message) {
        binding.apply {
            if (message.from == UID) {
                messageUserContainer.visibility = View.VISIBLE
                messageAnswerContainer.visibility = View.GONE
                messageTextUser.text = message.text
                messageUserTime.text = message.timestamp.asTime()
            } else {
                messageAnswerContainer.visibility = View.VISIBLE
                messageUserContainer.visibility = View.GONE
                messageTextAnswer.text = message.text
                messageAnswerTime.text = message.timestamp.asTime()
            }
        }
    }

}