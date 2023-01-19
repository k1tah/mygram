package com.example.mygram.presentation.activity.recyclerView.viewHolders

import com.example.mygram.domain.Message

interface MessageHolder {
    fun bind(message: Message)
}