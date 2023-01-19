package com.example.mygram.utils

import android.annotation.SuppressLint
import android.provider.ContactsContract
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mygram.APP_ACTIVITY
import com.example.mygram.R
import com.example.mygram.domain.Contact
import com.example.mygram.utils.TestTags.TEST_TAG_DATA
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.downloadAndSetImage(url: String) {
    Log.d(TEST_TAG_DATA, url)
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_baseline_image_24)
        .dontAnimate()
        .into(this)
}

fun CircleImageView.downloadAndSetImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_baseline_account_circle_24)
        .dontAnimate()
        .into(this)
}

fun AppCompatActivity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun showShortToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}

@SuppressLint("Range")
fun initPhoneContacts(): ArrayList<Contact>? {
    if (checkPermission(READ_CONTACTS, REQUEST_CODE_READ_CONTACTS)) {
        val contacts = arrayListOf<Contact>()
        val cursor = APP_ACTIVITY.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        cursor?.let {
            while (it.moveToNext()) {
                val phone =
                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val name = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val contact = Contact(
                    phone = phone.replace(Regex("[\\s, -]"), ""),
                    uid = name
                )
                contacts.add(contact)
            }
        }
        cursor?.close()
        return contacts
    }
    return null
}

fun String.asTime(): String {
    val date = Date(this.toLong() * 1000)
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(date)
}


