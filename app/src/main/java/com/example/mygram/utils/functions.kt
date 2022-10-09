package com.example.mygram.utils

import Const.TEST_TAG_DATA
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mygram.R
import de.hdodenhof.circleimageview.CircleImageView

fun CircleImageView.downloadAndSetImage(url: String){
    Log.d(TEST_TAG_DATA, "url is $url")
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_baseline_account_circle_24)
        .dontAnimate()
        .into(this)
}

fun AppCompatActivity.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
