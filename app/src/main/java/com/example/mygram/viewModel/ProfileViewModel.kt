package com.example.mygram.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mygram.repository.database.ProfileRepository
import kotlinx.coroutines.launch

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