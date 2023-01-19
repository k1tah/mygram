package com.example.mygram.data.repository

import android.util.Log
import com.example.mygram.data.repository.User.USER
import com.example.mygram.domain.Chat
import com.example.mygram.domain.Message
import com.example.mygram.utils.TestTags.TEST_TAG_DATA
import com.google.firebase.Timestamp

object MessagesRepository {

    val collectionMessages = databaseRefRoot.collection(NODE_MESSAGES)
    val collectionChats = databaseRefRoot.collection(NODE_CHATS)

    fun sendMessage(message: Message, uid: String) {
            val mapMessage = hashMapOf(
                CHILD_PHOTO to message.url,
                CHILD_FROM to message.from,
                CHILD_TYPE to message.type,
                CHILD_TEXT to message.text,
                CHILD_DATETIME to Timestamp.now().seconds
            )
            collectionMessages.document(UID).collection(uid).document().set(mapMessage)
                .addOnFailureListener {
                    Log.d(TEST_TAG_DATA, it.message.toString())
                }
            collectionMessages.document(uid).collection(UID).document().set(mapMessage)
    }

    fun saveToMainList(chat: Chat) {
            val userMap = hashMapOf<String, Any>(
                CHILD_TYPE to chat.type,
                CHILD_MESSAGE_TYPE to chat.messageType,
                CHILD_USERNAME to chat.name,
                CHILD_ID to chat.id,
                CHILD_PHOTO to chat.imageUrl,
                CHILD_TEXT to chat.lastMessage,
                CHILD_IS_CHECKED to IS_CHECKED,
                CHILD_FROM to chat.from
            )
            val answerMap = hashMapOf<String, Any>(
                CHILD_TYPE to chat.type,
                CHILD_MESSAGE_TYPE to chat.messageType,
                CHILD_USERNAME to USER.name,
                CHILD_ID to UID,
                CHILD_PHOTO to chat.imageUrl,
                CHILD_TEXT to chat.lastMessage,
                CHILD_IS_CHECKED to IS_NOT_CHECKED,
                CHILD_FROM to chat.from
            )
            collectionChats.document(UID).collection(UID).document(chat.id).set(userMap)
                .addOnFailureListener {
                    Log.d(TEST_TAG_DATA, it.message.toString())
                }
            collectionChats.document(chat.id).collection(chat.id).document(UID).set(answerMap)
                .addOnFailureListener {
                    Log.d(TEST_TAG_DATA, it.message.toString())
                }
    }

    fun isCheckedChat(uid: String, id: String){
        collectionChats.document(uid).collection(uid).document(id).update(CHILD_IS_CHECKED, IS_CHECKED)
    }

}