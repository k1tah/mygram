package com.example.mygram.viewModel

import Const.TEST_TAG_DATA
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mygram.domain.Message
import com.example.mygram.repository.MessagesRepository
import com.example.mygram.utils.*
import kotlinx.coroutines.launch

class MessagesViewModel : ViewModel() {

    fun sendMessage(message: Message, uid: String, photoUri: Uri?) {
        if (message.type == TYPE_TEXT) {
            viewModelScope.launch {
                MessagesRepository.sendMessage(message, uid)
            }
        }
        if (message.type == TYPE_PHOTO) {
            viewModelScope.launch {
                val path = storageRefRoot
                    .child(FOLDER_PROFILE_PHOTO)
                    .child(UID)
                photoUri?.let { uri ->
                    path.putFile(uri).addOnCompleteListener {
                        if (it.isSuccessful) {
                            path.downloadUrl.addOnCompleteListener { downloadTask ->
                                if (downloadTask.isSuccessful) {
                                    message.photoUrl = downloadTask.result.toString()
                                    Log.d(TEST_TAG_DATA, "photo is ${message.photoUrl}")
                                    MessagesRepository.sendMessage(message, uid)
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    class MessagesViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MessagesViewModel::class.java)) {
                @Suppress("UNCHEKED_CAST")
                return MessagesViewModel() as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}