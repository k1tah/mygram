package com.example.mygram.domain


data class Chat(
    var imageUrl: String = "",
    var name: String = "",
    var lastMessage: String = "",
    var id: String = "",
    var messageType: String = "",
    var type: String = "",
    var isChecked: Int = 0,
    var from: String = ""
)
