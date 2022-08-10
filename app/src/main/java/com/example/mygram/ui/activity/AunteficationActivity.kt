package com.example.mygram.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mygram.R
import com.example.mygram.databinding.ActivityAunteficationBinding

class AunteficationActivity : AppCompatActivity() {

    lateinit var binding: ActivityAunteficationBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAunteficationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_reg) as NavHostFragment
        navController = navHostFragment.navController
    }

}