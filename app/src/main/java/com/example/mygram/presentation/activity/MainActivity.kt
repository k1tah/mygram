package com.example.mygram.presentation.activity

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mygram.APP_ACTIVITY
import com.example.mygram.R
import com.example.mygram.data.repository.*
import com.example.mygram.data.repository.MessagesRepository.collectionChats
import com.example.mygram.data.repository.viewModel.ProfileViewModel
import com.example.mygram.databinding.ActivityMainBinding
import com.example.mygram.utils.*
import com.example.mygram.utils.MessagesNotification.CHANNEL_ID
import com.example.mygram.utils.MessagesNotification.CHANNEL_NAME
import com.example.mygram.utils.MessagesNotification.NOTIFICATION_MESSAGE_ID
import com.example.mygram.utils.TestTags.TEST_TAG_DATA
import com.google.firebase.firestore.DocumentChange
import com.kirich1409.androidnotificationdsl.NotificationImportance
import com.kirich1409.androidnotificationdsl.NotificationPriority
import com.kirich1409.androidnotificationdsl.channels.createNotificationChannels
import com.kirich1409.androidnotificationdsl.notification
import com.kirich1409.androidnotificationdsl.utils.activityPendingIntent

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var notificationManager: NotificationManagerCompat

    private val profileViewModel: ProfileViewModel by viewModels {
        ProfileViewModel.ProfileViewModelFactory()
    }

    init {
        APP_ACTIVITY = this
        initFirebase()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //navigation
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        //auth listener
        authStateListener()
        createNotificationChannels(this) {
            channel(CHANNEL_ID, CHANNEL_NAME, importance = NotificationImportance.DEFAULT)
        }
        notificationManager = NotificationManagerCompat.from(this)
    }

    override fun onStart() {
        profileViewModel.observeUser()
        checkPermission(READ_CONTACTS, REQUEST_CODE_READ_CONTACTS)
        initContacts()
        profileViewModel.observeContacts()
        observeUserMessages()
        profileViewModel.updateState(AppStates.ONLINE)
        super.onStart()
    }

    override fun onStop() {
        profileViewModel.updateState(AppStates.OFFLINE)
        super.onStop()
    }

    private fun authStateListener() {
        if (auth.currentUser == null) {
            val intent = Intent(this, AunteficationActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_CAMERA -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    return
                } else {
                    showToast("Permission is not granted")
                }
                return
            }
            REQUEST_CODE_READ_CONTACTS -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    initContacts()
                } else {
                    showToast("Permission is not granted")
                }
                return
            }
            else -> {
                return
            }
        }
    }

    private fun initContacts() {
        initPhoneContacts()?.let { profileViewModel.updateContacts(it) }
    }

    private fun observeUserMessages() {
        collectionChats.document(UID).collection(UID).addSnapshotListener { value, error ->
            if (error != null) {
                Log.w(TEST_TAG_DATA, "listen user messages error", error)
                return@addSnapshotListener
            }
            if (value != null) {
                for (dc in value.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> {
                        }
                        DocumentChange.Type.MODIFIED -> {
                            if (dc.document.data[CHILD_FROM] != UID) {
                                val notification = notification(
                                    this,
                                    CHANNEL_ID,
                                    smallIcon = R.drawable.ic_baseline_account_circle_24
                                ) {
                                    contentTitle = dc.document.data[CHILD_USERNAME] as String
                                    contentText = dc.document.data[CHILD_TEXT] as String
                                    priority = NotificationPriority.HIGH
                                    autoCancel = true
                                    contentIntent = activityPendingIntent(
                                        NOTIFICATION_MESSAGE_ID,
                                        MainActivity::class.java,
                                        PendingIntent.FLAG_IMMUTABLE
                                    )
                                }
                                notificationManager.notify(NOTIFICATION_MESSAGE_ID, notification)
                            }
                        }
                        DocumentChange.Type.REMOVED -> {
                        }
                    }
                }
            }
        }
    }

}