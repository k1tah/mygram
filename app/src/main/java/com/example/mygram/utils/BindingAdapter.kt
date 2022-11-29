package com.example.mygram.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mygram.domain.Contact
import com.example.mygram.domain.Message
import com.example.mygram.ui.adapter.ContactsAdapter
import com.example.mygram.ui.adapter.MessagesAdapter

@BindingAdapter("listContacts")
fun bindContactRecyclerView(recyclerView: RecyclerView, contacts: MutableList<Contact>?){
    val adapter = recyclerView.adapter as ContactsAdapter
    adapter.submitList(contacts)
}

@BindingAdapter("listMessages")
fun bindMessagesRecyclerView(recyclerView: RecyclerView, messages: MutableList<Message>?){
    val adapter = recyclerView.adapter as MessagesAdapter
    adapter.submitList(messages)
}