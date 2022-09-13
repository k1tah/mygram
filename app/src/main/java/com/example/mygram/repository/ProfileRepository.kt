package com.example.mygram.repository

import Const.TEST_TAG
import Const.TEST_TAG_AUTH
import Const.TEST_TAG_DATA
import android.net.Uri
import android.util.Log
import com.example.mygram.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ProfileRepository {

    private val collection = databaseRefRoot.collection(NODE_USERS)
    private val path = storageRefRoot
        .child(FOLDER_PROFILE_PHOTO)
        .child(UID)

    suspend fun updateBio(bio: String){
        withContext(Dispatchers.IO){
            collection.document(UID)
                .update(CHILD_BIO, bio)
                .addOnSuccessListener {
                    Log.d(TEST_TAG, bio)
                    Log.d(TEST_TAG, "update bio sucs")
                }
                .addOnFailureListener {
                    Log.d(TEST_TAG, "${it.message}")
                }
        }
    }

    suspend fun updateUserName(name: String){
        withContext(Dispatchers.IO) {
            collection.document(UID)
                .update(CHILD_USERNAME, name)
                .addOnSuccessListener {
                    Log.d(TEST_TAG, "update name sucs")
                }
                .addOnFailureListener {
                    Log.d(TEST_TAG, "${it.message}")
                }
        }
    }

    suspend fun addUser(user: HashMap<String, Any>){
        UID = auth.currentUser?.uid.toString()
        Log.d(TEST_TAG_AUTH, "User Id:$UID")
        withContext(Dispatchers.IO) {
            collection.document(UID)
                .set(user)
                .addOnSuccessListener {
                    Log.d(TEST_TAG, "user add to users")
                }
                .addOnFailureListener {
                    Log.w(TEST_TAG, "adding error ${it.message.toString()}")
                }
        }
    }

    suspend fun getUserFromFirebase(){
        withContext(Dispatchers.IO){
            collection.document(UID)
                .addSnapshotListener { value, error ->
                    if (error != null){
                        Log.w(TEST_TAG_DATA, "Listen failed.", error)
                        return@addSnapshotListener
                    }
                    if (value != null && value.exists()){
                        USER.name = value.data?.get(CHILD_USERNAME) as String
                        USER.phone = value.data?.get(CHILD_PHONE) as String
                        USER.bio = value.data?.get(CHILD_BIO) as String
                        USER.photoUrl = value.data?.get(CHILD_PHOTO) as String
                        Log.d(TEST_TAG_AUTH, "Current username: ${USER.name}")
                    } else {
                        Log.d(TEST_TAG_DATA, "Current data: null")
                    }
                }
        }
    }

    suspend fun updateState(appStates: AppStates){
        withContext(Dispatchers.IO){
            collection.document(UID).update(CHILD_STATE, appStates.state)
                .addOnSuccessListener {
                    USER.state = appStates.state
                }
        }
    }

    suspend fun updateUserPhoto(uri: Uri){
        withContext(Dispatchers.IO){
            path.putFile(uri)
                .addOnCompleteListener{
                    if (it.isSuccessful){
                        path.downloadUrl.addOnCompleteListener { downloadTask ->
                            if (downloadTask.isSuccessful){
                                val photoUrl = downloadTask.result.toString()
                                Log.d(TEST_TAG_DATA, "Current url: $photoUrl")
                                collection.document(UID).update(CHILD_PHOTO, photoUrl)
                                    .addOnCompleteListener { taskPhotoAddToDatabase ->
                                        if (taskPhotoAddToDatabase.isSuccessful){
                                            Log.d(TEST_TAG_DATA, "image add sus to database")
                                        }
                                    }
                                    .addOnFailureListener { addPhotoException ->
                                        Log.d(TEST_TAG_DATA, "${addPhotoException.message}")
                                    }
                            }
                        }
                        Log.d(TEST_TAG_DATA, "image add sus")
                    }
                }
                .addOnFailureListener {
                    Log.d(TEST_TAG_DATA, "image add failed ${it.message}")
                }
        }
    }


}