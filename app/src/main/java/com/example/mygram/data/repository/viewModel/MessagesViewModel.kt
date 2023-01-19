package com.example.mygram.data.repository.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mygram.data.repository.FOLDER_CHAT_FILES
import com.example.mygram.data.repository.MessagesRepository
import com.example.mygram.data.repository.storageRefRoot
import com.example.mygram.domain.Message
import com.example.mygram.utils.TestTags.TEST_TAG_DATA

class MessagesViewModel : ViewModel() {

    fun sendMessage(message: Message, uid: String) {
        MessagesRepository.sendMessage(message, uid)
    }

    fun sendFile(message: Message, uid: String, uri: Uri) {
        val path = storageRefRoot
            .child(FOLDER_CHAT_FILES)
            .child(message.messageKey)
        uri.let {
            path.putFile(it).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    path.downloadUrl.addOnCompleteListener { downloadTask ->
                        if (downloadTask.isSuccessful) {
                            message.url = downloadTask.result.toString()
                            Log.d(TEST_TAG_DATA, "file is ${message.url}")
                            MessagesRepository.sendMessage(message, uid)
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