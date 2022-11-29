package com.example.mygram.domain


data class Message(
    var text: String = "",
    var photoUrl: String = "",
    var type: String = "",
    var from: String = "",
    var timestamp: String = "",
    var count: Long = 0,
    )
