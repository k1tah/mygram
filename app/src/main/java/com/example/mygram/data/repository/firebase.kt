package com.example.mygram.data.repository

import android.annotation.SuppressLint
import com.example.mygram.domain.Contact
import com.example.mygram.domain.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

lateinit var UID: String
lateinit var auth: FirebaseAuth
@SuppressLint("StaticFieldLeak")
lateinit var databaseRefRoot: FirebaseFirestore
lateinit var storageRefRoot: StorageReference

//storage
const val FOLDER_PROFILE_PHOTO = "profile_image"
//database
//user
const val NODE_USERS = "users"
const val NODE_USER_PHONES = "userPhones"
const val NODE_USER_CONTACTS = "userContacts"
const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_USERNAME = "name"
const val CHILD_BIO = "bio"
const val CHILD_PHOTO = "photoUrl"
const val CHILD_STATE = "state"
//messages
const val FOLDER_CHAT_FILES = "chatFiles"
const val NODE_CHATS = "chats"
const val NODE_MESSAGES = "messages"
const val CHILD_TEXT = "text"
const val CHILD_TYPE = "type"
const val CHILD_FROM = "from"
const val CHILD_DATETIME = "time"
const val CHILD_IS_CHECKED = "isChecked"
//messages types
const val TYPE_TEXT = "text"
const val TYPE_PHOTO = "photo"
//message view types
const val VIEW_TEXT = 0
const val VIEW_IMAGE = 1
//chat type
const val TYPE_CHAT = "chat"
//chat values
const val CHILD_MESSAGE_TYPE = "messageType"
const val IS_CHECKED = 1
const val IS_NOT_CHECKED = 0

fun initFirebase() {
    auth = FirebaseAuth.getInstance()
    UID = auth.currentUser?.uid.toString()
    databaseRefRoot = Firebase.firestore
    storageRefRoot = FirebaseStorage.getInstance().reference
}

object User {
    val USER = User()
    val LIST_CONTACTS = mutableListOf<Contact>()
}