package com.example.mygram.data.repository.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.example.mygram.data.repository.*
import com.example.mygram.data.repository.User.LIST_CONTACTS
import com.example.mygram.data.repository.User.USER
import com.example.mygram.domain.Contact
import com.example.mygram.domain.User
import com.example.mygram.utils.*
import com.example.mygram.utils.TestTags.TEST_TAG_AUTH
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _user = MutableLiveData<User>()
    private val _listContacts = MutableLiveData<MutableList<Contact>>()


    val user: LiveData<User> = _user
    val listContacts: LiveData<MutableList<Contact>> = _listContacts

    fun updateUserName(name: String) {
        viewModelScope.launch {
            _user.value?.name = name
            ProfileRepository.updateUserName(name)
        }
    }

    fun observeContacts() {
        viewModelScope.launch {
            ProfileRepository.observeContacts()
            _listContacts.value = LIST_CONTACTS
        }
    }

    fun observeUser() {
        viewModelScope.launch {
            ProfileRepository.observeUser()
            _user.value = USER
        }
    }


    fun getUser() {
        viewModelScope.launch {
            ProfileRepository.getUser()
            _user.value = USER
        }
    }

    fun addUser(user: HashMap<String, Any>, userPhone: HashMap<String, String>) {
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

    fun updateUserPhoto(uri: Uri) {
        viewModelScope.launch {
            val path = storageRefRoot
                .child(FOLDER_PROFILE_PHOTO)
                .child(UID)
            path.putFile(uri).addOnCompleteListener {
                if (it.isSuccessful) {
                    path.downloadUrl.addOnCompleteListener { downloadTask ->
                        if (downloadTask.isSuccessful) {
                            val photoUrl = downloadTask.result.toString()
                            ProfileRepository.updateUserPhoto(photoUrl)
                        }
                    }
                }
            }
        }
    }

    fun signOut() {
        auth.signOut()
        USER.apply {
            name = ""
            phone = ""
            bio = ""
            photoUrl = ""
        }
        Log.d(TEST_TAG_AUTH, USER.name)
    }

    fun updateState(appStates: AppStates) {
        viewModelScope.launch {
            ProfileRepository.updateState(appStates)
        }
    }

    fun updateContacts(contacts: ArrayList<Contact>) {
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

    class ProfileViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                @Suppress("UNCHEKED_CAST")
                return ProfileViewModel() as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}