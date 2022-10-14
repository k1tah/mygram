package com.example.mygram.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mygram.databinding.ItemContactBinding
import com.example.mygram.domain.Contact
import com.example.mygram.utils.downloadAndSetImage


class ContactsAdapter: ListAdapter<Contact, ContactsAdapter.ContactHolder>(DiffCallback)
    {

    class ContactHolder(private var binding: ItemContactBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.apply{
                contactName.text = contact.uid
                contactState.text = contact.state
                contactImage.downloadAndSetImage(contact.photoUrl)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        return ContactHolder(ItemContactBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact)
    }

    companion object {
        object DiffCallback: DiffUtil.ItemCallback<Contact>(){
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return  oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.phone == newItem.phone
            }

        }
    }


}