package com.example.mygram.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(var name: String = "",
                val uid: String,
                @PrimaryKey(autoGenerate = true) val id: Int = 0,
                var phone: String = "",
                var bio: String = "",
                var status: String = "",
                var photoUrl: String = "")
