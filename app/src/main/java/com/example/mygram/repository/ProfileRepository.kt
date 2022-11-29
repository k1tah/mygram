package com.example.mygram.repository

import Const.TEST_TAG
import Const.TEST_TAG_AUTH
import Const.TEST_TAG_DATA
import android.util.Log
import com.example.mygram.domain.Contact
import com.example.mygram.utils.*
import com.example.mygram.utils.User.LIST_CONTACTS
import com.example.mygram.utils.User.USER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


object ProfileRepository {

    private val collectionUsers = databaseRefRoot.collection(NODE_USERS)
    private val collectionPhones = databaseRefRoot.collection(NODE_USER_PHONES)
    private val collectionUserContacts = databaseRefRoot.collection(NODE_USER_CONTACTS)

    suspend fun updateBio(bio: String){
        withContext(Dispatchers.IO){
            collectionUsers.document(UID)
                .update(CHILD_BIO, bio)
                .addOnSuccessListener {
                    Log.d(TEST_TAG, bio)
                    Log.d(TEST_TAG, "update bio sus")
                }
                .addOnFailureListener {
                    Log.d(TEST_TAG, "${it.message}")
                }
        }
    }

    suspend fun updateUserName(name: String){
        withContext(Dispatchers.IO) {
            collectionUsers.document(UID)
                .update(CHILD_USERNAME, name)
                .addOnSuccessListener {
                    Log.d(TEST_TAG, "update name sus")
                }
                .addOnFailureListener {
                    Log.d(TEST_TAG, "${it.message}")
                }
        }
    }

    suspend fun addUser(user: HashMap<String, Any>, userPhone: HashMap<String, String>){
        UID = auth.currentUser?.uid.toString()
        Log.d(TEST_TAG_AUTH, "User Id:$UID")
        withContext(Dispatchers.IO) {
            collectionUsers.document(UID)
                .set(user)
                .addOnSuccessListener {
                    Log.d(TEST_TAG, "user add to users")
                }
                .addOnFailureListener {
                    Log.w(TEST_TAG, "adding error ${it.message.toString()}")
                }
            collectionPhones.document(UID)
                .set(userPhone)
                .addOnSuccessListener {
                    Log.d(TEST_TAG, "userPhone add to userPhones")
                }
                .addOnFailureListener {
                    Log.w(TEST_TAG, "adding phone error ${it.message.toString()}")
                }
        }
    }
    suspend fun getUser(){
        withContext(Dispatchers.IO){
            collectionUsers.document(UID).get()
                .addOnSuccessListener {
                    USER.name = it.data?.get(CHILD_USERNAME) as String
                    USER.phone = it.data?.get(CHILD_PHONE) as String
                    USER.photoUrl = it.data?.get(CHILD_PHOTO) as String
                    USER.bio = it.data?.get(CHILD_BIO) as String
                }
                .addOnFailureListener { exception ->
                    Log.w(TEST_TAG, "get user error: ${exception.message}")
                }
        }
    }

    suspend fun observeUser(){
        withContext(Dispatchers.IO){
            collectionUsers.document(UID)
                .addSnapshotListener { value, error ->
                    if (error != null){
                        Log.w(TEST_TAG_DATA, "Listen failed.", error)
                        return@addSnapshotListener
                    }
                    if (value != null && value.exists()){
                        USER.name = value.data?.get(CHILD_USERNAME) as String
                        USER.phone = value.data?.get(CHILD_PHONE) as String
                        USER.bio = value.data?.get(CHILD_BIO) as String
                        USER.photoUrl = value.data?.get(CHILD_PHOTO) as String
                    } else {
                        Log.d(TEST_TAG_DATA, "Current data: null")
                    }
                }
        }
    }

    suspend fun updateState(appStates: AppStates){
        withContext(Dispatchers.IO){
            collectionUsers.document(UID).update(CHILD_STATE, appStates.state)
                .addOnSuccessListener {
                    USER.state = appStates.state
                }
        }
    }

    fun updateUserPhoto(photoUrl: String) {
            collectionUsers.document(UID).update(CHILD_PHOTO, photoUrl)
                .addOnCompleteListener { taskPhotoAddToDatabase ->
                    if (taskPhotoAddToDatabase.isSuccessful) {
                        USER.photoUrl = photoUrl
                    }
                }
                .addOnFailureListener { addPhotoException ->
                    Log.d(TEST_TAG_DATA, "${addPhotoException.message}")
                }
    }

    suspend fun observeContacts(){
        withContext(Dispatchers.IO){
            collectionUserContacts.document(UID).addSnapshotListener { value, error ->
                if (error != null){
                    Log.w(TEST_TAG, "observe contacts failed.", error)
                    return@addSnapshotListener
                }
                if (value != null){
                    value.data?.forEach {
                        val contact = Contact(uid = it.value.toString())
                        if (!LIST_CONTACTS.contains(contact)){
                            LIST_CONTACTS.add(contact)
                        }
                    }
                } else {
                    Log.d(TEST_TAG_DATA, "Current phones data: null")
                }
            }
        }
    }

    suspend fun getUserContacts(){
        withContext(Dispatchers.IO){
            collectionUserContacts.document(UID).get()
                .addOnSuccessListener {
                    it.data?.forEach { userContact ->
                        val contact = Contact(
                            uid = userContact.value.toString()
                        )
                        if (!LIST_CONTACTS.contains(contact)){
                            LIST_CONTACTS.add(contact)
                        }
                    }
                }
        }
    }

    suspend fun updateContacts(contacts: ArrayList<Contact>){
        withContext(Dispatchers.IO){
            collectionPhones.addSnapshotListener{ value, error ->
                if (error != null){
                    Log.w(TEST_TAG, "Listen phones failed.", error)
                    return@addSnapshotListener
                }
                if (value != null){
                    val mapContacts = hashMapOf<String, String>()
                    value.documents.forEach { documentSnapshot ->
                        documentSnapshot.data?.forEach { mapPhones ->
                            contacts.forEach { contact ->
                                if (mapPhones.value == contact.phone){
                                    mapContacts[contact.phone] = mapPhones.key
                                }
                            }
                        }
                    }
                    collectionUserContacts.document(UID)
                        .set(mapContacts)
                        .addOnFailureListener {
                            Log.w(TEST_TAG_DATA, "contacts adding failed: ${it.message}")
                        }
                } else {
                    Log.d(TEST_TAG_DATA, "Current phones data: null")
                }
            }

        }
    }

}
