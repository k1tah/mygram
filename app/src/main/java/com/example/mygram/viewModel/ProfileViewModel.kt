package com.example.mygram.viewModel

import Const.TEST_TAG_AUTH
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.example.mygram.domain.Contact
import com.example.mygram.domain.User
import com.example.mygram.repository.ProfileRepository
import com.example.mygram.ui.activity.MainActivity
import com.example.mygram.utils.AppStates
import com.example.mygram.utils.User.LISTCONTACTS
import com.example.mygram.utils.User.USER
import com.example.mygram.utils.auth
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ProfileViewModel: ViewModel() {

    private val _user = MutableLiveData<User>()
    private val _listContacts = MutableLiveData<MutableList<Contact>>()

    val user: LiveData<User> = _user
    val listContacts: LiveData<MutableList<Contact>> = _listContacts

    private val profileRepository = ProfileRepository()

    fun updateUserName(name: String){
        viewModelScope.launch {
            _user.value?.name = name
            profileRepository.updateUserName(name)
        }
    }



    fun observeContacts(){
        viewModelScope.launch {
            profileRepository.observeContacts()
            _listContacts.value = LISTCONTACTS
        }
    }

    fun observeUser(){
        viewModelScope.launch {
            profileRepository.observeUser()
            _user.value = USER
        }
    }

    fun getUser(){
        viewModelScope.launch {
            profileRepository.getUser()
            _user.value = USER
        }
    }

    fun addUser(user: HashMap<String, Any>, userPhone: HashMap<String, String>){
        viewModelScope.launch {
            profileRepository.addUser(user, userPhone)
        }
    }

    fun updateBio(bio: String) {
        viewModelScope.launch {
            profileRepository.updateBio(bio)
            _user.value?.bio = bio
        }
    }

    fun updateUserPhoto(uri: Uri){
        viewModelScope.launch {
            profileRepository.updateUserPhoto(uri)
        }
    }

    fun signOut(){
        auth.signOut()
        USER.apply {
            name = ""
            phone = ""
            bio = ""
            photoUrl = ""
        }
        Log.d(TEST_TAG_AUTH, USER.name)
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
                Log.d(TEST_TAG_AUTH, "user deleted")
            }
                .addOnFailureListener {
                    Log.d(TEST_TAG_AUTH, "${it.message}")
                }
        }
        .addOnFailureListener {
               Log.d(TEST_TAG_AUTH, "${it.message}")
        }
    }

    fun updateState(appStates: AppStates){
        viewModelScope.launch {
            profileRepository.updateState(appStates)
        }
    }

    fun updateContacts(contacts: ArrayList<Contact>){
        viewModelScope.launch {
            profileRepository.updateContacts(contacts)
        }
    }

    fun getUserContacts() {
        viewModelScope.launch {
            profileRepository.getUserContacts()
            _listContacts.value = LISTCONTACTS
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