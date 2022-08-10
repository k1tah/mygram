package com.example.mygram.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
    val text: String = "",
    @PrimaryKey(autoGenerate = true) val id : Int
    )
