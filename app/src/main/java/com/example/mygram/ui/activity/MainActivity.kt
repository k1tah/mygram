package com.example.mygram.ui.activity

import Const.TEST_TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mygram.APP_ACTIVITY
import com.example.mygram.R
import com.example.mygram.databinding.ActivityMainBinding
import com.example.mygram.utils.AppStates
import com.example.mygram.utils.auth
import com.example.mygram.utils.initFirebase
import com.example.mygram.viewModel.ProfileViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val profileViewModel: ProfileViewModel by viewModels {
        ProfileViewModel.ProfileViewModelFactory()
    }
    init {
        APP_ACTIVITY = this
        initFirebase()
        Log.d(TEST_TAG, "init firebase")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

    }

    override fun onStart() {
        authStateListener()
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

    override fun onBackPressed() {
        this.finish()
        super.onBackPressed()
    }

}