package com.example.mygram.viewModel

import Const.TEST_TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mygram.repository.ProfileRepository
import com.example.mygram.ui.activity.MainActivity
import com.example.mygram.utils.USER
import com.example.mygram.utils.auth
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ProfileViewModel(): ViewModel() {

    val profileRepository = ProfileRepository()

    fun updateUserName(name: String){
        viewModelScope.launch {
            profileRepository.updateUserName(name)
        }
    }

    fun getUserFromFirebase(){
        viewModelScope.launch {
            profileRepository.getUserFromFirebase()
        }
    }

    fun addUser(user: HashMap<String, Any>){
        viewModelScope.launch {
            profileRepository.addUser(user)
        }
    }

    fun updateBio(bio: String) {
        viewModelScope.launch {
            profileRepository.updateBio(bio)
        }
    }

    fun signOut(){
        auth.signOut()
    }

    fun deleteAccount(code: String, activity: MainActivity, callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            USER.phone,
            60,
            TimeUnit.SECONDS,
            activity,
            callback
        )
        val credential = PhoneAuthProvider.getCredential(USER.phone, code)
        val user = auth.currentUser!!
        user.reauthenticate(credential).addOnSuccessListener {
            user.delete().addOnSuccessListener {
                Log.d(TEST_TAG, "user deleted")
            }
                .addOnFailureListener {
                    Log.d(TEST_TAG, "${it.message}")
                }
        }
        .addOnFailureListener {
               Log.d(TEST_TAG, "${it.message}")
        }
    }

    class ProfileViewModelFactory: ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)){
                @Suppress("UNCHEKED_CAST")
                return ProfileViewModel() as T
            }else{
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}