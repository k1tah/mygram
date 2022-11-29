package com.example.mygram.utils

import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mygram.APP_ACTIVITY

const val CAMERA = android.Manifest.permission.CAMERA
const val READ_CONTACTS = android.Manifest.permission.READ_CONTACTS
const val REQUEST_CODE_READ_CONTACTS = 333
const val REQUEST_CODE_CAMERA = 444

fun checkPermission(permission: String, requestCode: Int) : Boolean{
    return if (Build.VERSION.SDK_INT >= 23
        && ContextCompat.checkSelfPermission(APP_ACTIVITY, permission) != PackageManager.PERMISSION_GRANTED)
    {
        ActivityCompat.requestPermissions(APP_ACTIVITY, arrayOf(permission), requestCode)
        false
    } else true
}