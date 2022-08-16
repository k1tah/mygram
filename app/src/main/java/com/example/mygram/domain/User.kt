package com.example.mygram.domain

data class User(var name: String = "",
                var uid: String = "",
                var phone: String = "",
                var bio: String = "",
                var status: String = "",
                var photoUrl: String = "")
