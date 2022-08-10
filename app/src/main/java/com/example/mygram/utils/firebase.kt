package com.example.mygram.utils

import android.annotation.SuppressLint
import com.example.mygram.domain.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

lateinit var USER: User
lateinit var UID: String
lateinit var auth: FirebaseAuth
@SuppressLint("StaticFieldLeak")
lateinit var databaseRefRoot: FirebaseFirestore

//database
const val NODE_USERS = "users"
const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_USERNAME = "name"


fun initFirebase(){
    auth = FirebaseAuth.getInstance()
    UID = auth.currentUser?.uid.toString()
    databaseRefRoot = Firebase.firestore
}