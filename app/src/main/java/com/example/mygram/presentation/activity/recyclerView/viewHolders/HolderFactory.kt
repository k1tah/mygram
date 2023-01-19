package com.example.mygram.presentation.activity.recyclerView.viewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mygram.data.repository.VIEW_IMAGE
import com.example.mygram.databinding.ItemImageMessageBinding
import com.example.mygram.databinding.ItemTextMessageBinding


class HolderFactory {
    companion object {
        fun getHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                 VIEW_IMAGE ->  {
                    ImageMessageViewHolder(
                        ItemImageMessageBinding.inflate(LayoutInflater.from(parent.context))
                    )
                }
                else -> {
                    TextMessageViewHolder(
                        ItemTextMessageBinding.inflate(LayoutInflater.from(parent.context))
                    )
                }
            }
        }
    }
}