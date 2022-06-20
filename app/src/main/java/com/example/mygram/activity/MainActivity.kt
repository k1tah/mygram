package com.example.mygram.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.mygram.R
import com.example.mygram.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var bindingClass: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        val toolbar = bindingClass.toolbar
        val drawerLayout = bindingClass.drawerLayout

        toolbar.setNavigationOnClickListener {
            drawerLayout.open()
        }
    }

}