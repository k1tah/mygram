package com.example.mygram.repository.database

import Const.TEST_TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.mygram.domain.User
import com.example.mygram.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProfileRepository(private val database: AppDatabase) {

    //private lateinit var name: String
    //private lateinit var phone: String
/*
    init {
        databaseRefRoot.collection(NODE_USERS).document(UID)
            .get()
            .addOnSuccessListener {
                name = it.get(CHILD_USERNAME).toString()
                Log.d(TEST_TAG, name)
                phone = it.get(CHILD_PHONE).toString()
                Log.d(TEST_TAG, phone)
            }
            .addOnFailureListener {
                Log.w(TEST_TAG, "${it.message}")
            }
    }

    suspend fun insertUser(){
        withContext(Dispatchers.IO){
            database.profileDao().insertUser(
                User(
                    name, phone
                )
            )
        }
    }

    suspend fun updateUserName(name: String){
        withContext(Dispatchers.IO) {
            databaseRefRoot.collection("users").document(UID).update(CHILD_USERNAME, name)
                .addOnSuccessListener {
                    Log.d(TEST_TAG, "update sucs")
                }
                .addOnFailureListener {
                    Log.d(TEST_TAG, "${it.message}")
                }
        }
    }

    suspend fun addUser(user: HashMap<String, Any>){
        withContext(Dispatchers.IO) {
            databaseRefRoot.collection(NODE_USERS)
                .document(UID)
                .set(user)
                .addOnSuccessListener {
                    Log.d(TEST_TAG, "user add to users")
                }
                .addOnFailureListener {
                    Log.w(TEST_TAG, "adding error ${it.message.toString()}")
                }
        }
    }
*/

}