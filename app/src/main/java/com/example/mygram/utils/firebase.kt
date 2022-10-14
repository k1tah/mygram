package com.example.mygram.utils

import android.annotation.SuppressLint
import android.provider.ContactsContract
import com.example.mygram.APP_ACTIVITY
import com.example.mygram.domain.Contact
import com.example.mygram.domain.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

lateinit var UID: String
lateinit var auth: FirebaseAuth
@SuppressLint("StaticFieldLeak")
lateinit var databaseRefRoot: FirebaseFirestore
lateinit var storageRefRoot: StorageReference

//storage
const val FOLDER_PROFILE_PHOTO = "profile_image"
//database
const val NODE_USERS = "users"
const val NODE_USER_PHONES = "userPhones"
const val NODE_USER_CONTACTS = "userContacts"
const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_USERNAME = "name"
const val CHILD_BIO = "bio"
const val CHILD_PHOTO = "photoUrl"
const val CHILD_STATE = "state"


fun initFirebase() {
    auth = FirebaseAuth.getInstance()
    UID = auth.currentUser?.uid.toString()
    databaseRefRoot = Firebase.firestore
    storageRefRoot = FirebaseStorage.getInstance().reference
}

@SuppressLint("Range")
fun initPhoneContacts(): ArrayList<Contact>? {
    if (checkPermission(READ_CONTACTS)) {
        val contacts = arrayListOf<Contact>()
        val cursor = APP_ACTIVITY.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        cursor?.let {
            while (it.moveToNext()) {
                val name = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phone = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val contact = Contact(
                    name,
                    phone.replace(Regex("[\\s, -]"), "")
                )
                contacts.add(contact)
            }
        }
        cursor?.close()
        return contacts
    }
    return null
}

object User {
    val USER = User()
    val LISTCONTACTS = mutableListOf<Contact>()
}