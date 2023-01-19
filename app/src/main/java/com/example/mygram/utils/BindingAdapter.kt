package com.example.mygram.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mygram.domain.Contact
import com.example.mygram.presentation.activity.recyclerView.adapter.ContactsAdapter

@BindingAdapter("listContacts")
fun bindContactRecyclerView(recyclerView: RecyclerView, contacts: MutableList<Contact>?){
    val adapter = recyclerView.adapter as ContactsAdapter
    adapter.submitList(contacts)
}
