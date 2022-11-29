package com.example.mygram.ui.adapter

import Const.TEST_TAG_DATA
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mygram.databinding.ItemContactBinding
import com.example.mygram.domain.Contact
import com.example.mygram.ui.ContactsFragmentDirections
import com.example.mygram.utils.*


class ContactsAdapter: ListAdapter<Contact, ContactsAdapter.ContactHolder>(DiffCallback)
    {

    class ContactHolder(private var binding: ItemContactBinding): RecyclerView.ViewHolder(binding.root) {

        lateinit var name: String
        lateinit var state: String
        lateinit var photo: String

        fun bind(contactUid: String) {
            val collectionUsers = databaseRefRoot.collection(NODE_USERS)
            collectionUsers.document(contactUid).get()
                .addOnSuccessListener {
                    name = it.data?.get(CHILD_USERNAME) as String
                    state = it.data?.get(CHILD_STATE) as String
                    photo = it.data?.get(CHILD_PHOTO) as String
                    binding.apply{
                        contactName.text = name
                        contactState.text = state
                        contactImage.downloadAndSetImage(photo)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TEST_TAG_DATA, "get contact error: ${exception.message}")
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        return ContactHolder(ItemContactBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        val contactUid = getItem(position).uid
        holder.bind(contactUid)
        holder.itemView.setOnClickListener{ view ->
            val action = ContactsFragmentDirections.actionContactsFragmentToSingleChatFragment(contactUid)
            view.findNavController().navigate(action)
        }
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