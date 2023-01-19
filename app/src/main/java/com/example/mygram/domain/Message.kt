package com.example.mygram.domain


data class Message(
    var text: String = "",
    var url: String = "",
    var type: String = "",
    var from: String = "",
    var timestamp: String = "",
    val messageKey: String = ""
    )
