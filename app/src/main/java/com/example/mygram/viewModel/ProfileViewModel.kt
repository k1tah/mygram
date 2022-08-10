package com.example.mygram.viewModel

import Const.TEST_TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mygram.repository.database.ProfileDao
import com.example.mygram.utils.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ProfileViewModel(private val profileDao: ProfileDao): ViewModel() {

    fun updateUserName(name: String){
        viewModelScope.launch {
            databaseRefRoot.collection(NODE_USERS).document(UID).update(CHILD_USERNAME, name)
                .addOnSuccessListener {
                    Log.d(TEST_TAG, "update sucs")
                }
                .addOnFailureListener {
                    Log.d(TEST_TAG, "${it.message} pizdec")
                }
        }
    }

    fun addUser(user: HashMap<String, Any>){
        viewModelScope.launch {
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

    class ProfileViewModelFactory(private val profileDao: ProfileDao): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)){
                @Suppress("UNCHEKED_CAST")
                return ProfileViewModel(profileDao) as T
            }else{
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}