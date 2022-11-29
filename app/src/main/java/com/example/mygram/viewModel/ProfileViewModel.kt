package com.example.mygram.viewModel

import Const.TEST_TAG_AUTH
import Const.TEST_TAG_DATA
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.example.mygram.domain.Contact
import com.example.mygram.domain.User
import com.example.mygram.repository.ProfileRepository
import com.example.mygram.ui.activity.MainActivity
import com.example.mygram.utils.*
import com.example.mygram.utils.User.LIST_CONTACTS
import com.example.mygram.utils.User.USER
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ProfileViewModel: ViewModel() {

    private val collectionUsers = databaseRefRoot.collection(NODE_USERS)

    private val _user = MutableLiveData<User>()
    private val _listContacts = MutableLiveData<MutableList<Contact>>()


    val user: LiveData<User> = _user
    val listContacts: LiveData<MutableList<Contact>> = _listContacts

    fun updateUserName(name: String){
        viewModelScope.launch {
            _user.value?.name = name
            ProfileRepository.updateUserName(name)
        }
    }

    fun observeContacts(){
        viewModelScope.launch {
            ProfileRepository.observeContacts()
            _listContacts.value = LIST_CONTACTS
        }
    }

    fun observeUser(){
        viewModelScope.launch {
            ProfileRepository.observeUser()
            _user.value = USER
        }
    }


    fun getUser(){
        viewModelScope.launch {
            ProfileRepository.getUser()
            _user.value = USER
        }
    }

    fun addUser(user: HashMap<String, Any>, userPhone: HashMap<String, String>){
        viewModelScope.launch {
            ProfileRepository.addUser(user, userPhone)
        }
    }

    fun updateBio(bio: String) {
        viewModelScope.launch {
            ProfileRepository.updateBio(bio)
            _user.value?.bio = bio
        }
    }

    fun updateUserPhoto(uri: Uri){
        viewModelScope.launch {
            val path = storageRefRoot
                .child(FOLDER_PROFILE_PHOTO)
                .child(UID)
            path.putFile(uri).addOnCompleteListener {
                if (it.isSuccessful){
                    path.downloadUrl.addOnCompleteListener { downloadTask ->
                        if (downloadTask.isSuccessful){
                            val photoUrl = downloadTask.result.toString()
                            ProfileRepository.updateUserPhoto(photoUrl)
                        }
                    }
                }
            }
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
            ProfileRepository.updateState(appStates)
        }
    }

    fun updateContacts(contacts: ArrayList<Contact>){
        viewModelScope.launch {
            ProfileRepository.updateContacts(contacts)
        }
    }

    fun getUserContacts() {
        viewModelScope.launch {
            ProfileRepository.getUserContacts()
            _listContacts.value = LIST_CONTACTS
        }
    }

    fun observeContact(uid: String){
        viewModelScope.launch {
            collectionUsers.document(uid)
                .addSnapshotListener { value, error ->
                    if (error != null){
                        Log.w(TEST_TAG_DATA, "Listen failed.", error)
                        return@addSnapshotListener
                    }
                    if (value != null && value.exists()){
                        //_chatContact.value?.name = value.data?.get(CHILD_USERNAME) as String
                        //_chatContact.value?.phone = value.data?.get(CHILD_PHONE) as String
                        //_chatContact.value?.photoUrl = value.data?.get(CHILD_PHOTO) as String
                        //_chatContact.value?.name?.let { it1 -> Log.d(TEST_TAG_DATA, it1) }
                    } else {
                        Log.d(TEST_TAG_DATA, "Current data: null")
                    }
                }
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