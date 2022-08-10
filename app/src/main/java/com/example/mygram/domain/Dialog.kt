package com.example.mygram.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dialog(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val imageUrl: String = "",
    val name: String = "",
    val lastMessage: String = ""
)
