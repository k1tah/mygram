package com.example.mygram.repository

import Const.TEST_TAG_DATA
import android.util.Log
import com.example.mygram.domain.Message
import com.example.mygram.utils.*
import com.google.firebase.Timestamp

object MessagesRepository {

    val collectionMessages = databaseRefRoot.collection(NODE_MESSAGES)

    fun sendMessage(message: Message, uid: String) {
        val mapMessage = hashMapOf(
            CHILD_PHOTO to message.photoUrl,
            CHILD_FROM to message.from,
            CHILD_TYPE to message.type,
            CHILD_TEXT to message.text,
            CHILD_DATETIME to Timestamp.now().seconds,
            CHILD_COUNT to message.count + 1
        )
        collectionMessages.document(UID).collection(uid).document().set(mapMessage)
            .addOnFailureListener {
                Log.d(TEST_TAG_DATA, it.message.toString())
            }
        collectionMessages.document(uid).collection(UID).document().set(mapMessage)
    }
}