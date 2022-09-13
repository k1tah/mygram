package com.example.mygram.utils

import android.annotation.SuppressLint
import com.example.mygram.domain.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

lateinit var USER: User
lateinit var UID: String
lateinit var auth: FirebaseAuth
@SuppressLint("StaticFieldLeak")
lateinit var databaseRefRoot: FirebaseFirestore
lateinit var storageRefRoot: StorageReference

//storage
const val FOLDER_PROFILE_PHOTO = "profile_image"
//database
const val NODE_USERS = "users"
const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_USERNAME = "name"
const val CHILD_BIO = "bio"
const val CHILD_PHOTO = "photoUrl"
const val CHILD_STATE = "state"


fun initFirebase(){
    USER = User()
    auth = FirebaseAuth.getInstance()
    UID = auth.currentUser?.uid.toString()
    databaseRefRoot = Firebase.firestore
    storageRefRoot = FirebaseStorage.getInstance().reference
}