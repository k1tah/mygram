package com.example.mygram

import android.app.Application
import android.content.Context
import com.example.mygram.repository.database.AppDatabase

class MyGrammApplication(): Application() {
    val context: Context = this.applicationContext
    val database: AppDatabase by lazy { AppDatabase.getDatabase(context) }
}