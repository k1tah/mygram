package com.example.mygram.repository

import Const.TEST_TAG
import android.util.Log
import com.example.mygram.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ProfileRepository() {
    private val collection = databaseRefRoot.collection(NODE_USERS)

    suspend fun updateBio(bio: String){
        withContext(Dispatchers.IO){
            collection.document(UID)
                .update(CHILD_BIO, bio)
                .addOnSuccessListener {
                    Log.d(TEST_TAG, "update bio sucs")
                }
                .addOnFailureListener {
                    Log.d(TEST_TAG, "${it.message}")
                }
        }
    }

    suspend fun updateUserName(name: String){
        withContext(Dispatchers.IO) {
            collection.document(UID)
                .update(CHILD_USERNAME, name)
                .addOnSuccessListener {
                    Log.d(TEST_TAG, "update name sucs")
                }
                .addOnFailureListener {
                    Log.d(TEST_TAG, "${it.message}")
                }
        }
    }

    suspend fun addUser(user: HashMap<String, Any>){
        UID = auth.currentUser?.uid.toString()
        Log.d(TEST_TAG, "User Id:$UID")
        withContext(Dispatchers.IO) {
            collection.document(UID)
                .set(user)
                .addOnSuccessListener {
                    Log.d(TEST_TAG, "user add to users")
                }
                .addOnFailureListener {
                    Log.w(TEST_TAG, "adding error ${it.message.toString()}")
                }
        }
    }

    suspend fun getUserFromFirebase(){
        withContext(Dispatchers.IO){
            collection.document(UID)
                .addSnapshotListener { value, error ->
                    if (error != null){
                        Log.w(TEST_TAG, "Listen failed.", error)
                        return@addSnapshotListener
                    }
                    if (value != null && value.exists()){
                        USER.name = value.data?.get(CHILD_USERNAME) as String
                        USER.phone = value.data?.get(CHILD_PHONE) as String
                        USER.bio = value.data?.get(CHILD_BIO) as String
                    } else {
                        Log.d(TEST_TAG, "Current data: null")
                    }
                }
        }
    }


}