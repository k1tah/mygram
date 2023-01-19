package com.example.mygram.presentation.activity.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mygram.R
import com.example.mygram.data.repository.*
import com.example.mygram.data.repository.User.USER
import com.example.mygram.data.repository.viewModel.ProfileViewModel
import com.example.mygram.databinding.FragmentMainBinding
import com.example.mygram.domain.Chat
import com.example.mygram.presentation.activity.recyclerView.adapter.MainAdapter
import com.example.mygram.utils.TestTags.TEST_TAG_DATA
import com.example.mygram.utils.downloadAndSetImage
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView


class MainFragment : Fragment() {


    //viewBinding
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    //navigation
    private var _navController: NavController? = null
    private val navController get() = _navController!!

    //viewModel
    //private val messageViewModel: MessagesViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()

    //navView
    private lateinit var navigationView: NavigationView
    private lateinit var header: View

    //recyclerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainAdapter
    private val listChats = mutableListOf<Chat>()
    private val listId = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _navController = findNavController()
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main, container, false
        )

        recyclerView = binding.mainList
        adapter = MainAdapter()
        recyclerView.adapter = adapter

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val owner = viewLifecycleOwner
        profileViewModel.user.observe(owner) {
            header.findViewById<TextView>(R.id.drawer_layout_name).text = it.name
            header.findViewById<TextView>(R.id.drawer_layout_phone).text = it.phone
            header.findViewById<CircleImageView>(R.id.account_image)
                .downloadAndSetImage(it.photoUrl)
        }

        navigationView = binding.navigationView
        header = navigationView.getHeaderView(
            0
        )

        val accountImage = header.findViewById<CircleImageView>(R.id.account_image)
        val toolbar = binding.toolbar
        val drawerLayout = binding.drawerLayout

        observeChats()

        toolbar.setNavigationOnClickListener {
            updateUserData()
            drawerLayout.open()
        }

        accountImage.setOnClickListener {
            navController.navigate(R.id.accountInfoFragment)
            drawerLayout.close()
        }

        navigationView.setupWithNavController(navController)
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.settings -> {
                    navController.navigate(R.id.accountInfoFragment)
                    drawerLayout.close()
                }
                R.id.contacts -> {
                    navController.navigate(R.id.contactsFragment)
                    drawerLayout.close()
                }
            }
            true
        }

    }

    override fun onDestroyView() {
        _navController = null
        _binding = null
        super.onDestroyView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeChats() {
        MessagesRepository.collectionChats.document(UID).collection(UID).addSnapshotListener { collection, error ->
            if (error != null) {
                Log.w(TEST_TAG_DATA, "Listen chats failed.", error)
                return@addSnapshotListener
            }
            if (collection != null) {
                collection.documents.forEach { documentSnapshot ->
                    listId.clear()
                    listChats.clear()
                    val chat = Chat()
                    documentSnapshot.data?.forEach {
                        when (it.key){
                            CHILD_TEXT -> {
                                chat.lastMessage = it.value.toString()
                            }
                            CHILD_TYPE -> {
                                chat.type = it.value.toString()
                            }
                            CHILD_MESSAGE_TYPE -> {
                                chat.messageType = it.value.toString()
                            }
                            CHILD_PHOTO -> {
                                chat.imageUrl = it.value.toString()
                            }
                            CHILD_ID -> {
                                chat.id = it.value.toString()
                                listId.add(chat.id)
                            }
                            CHILD_USERNAME -> {
                                chat.name = it.value.toString()
                            }
                            CHILD_IS_CHECKED -> {
                                chat.isChecked = (it.value as Long).toInt()
                            }
                        }
                        if (listId.size == listChats.size + 1){
                            listChats.add(chat)
                        }
                    }
                }
                adapter.submitList(listChats)
                adapter.notifyDataSetChanged()
            } else {
                Log.d(TEST_TAG_DATA, "Current data: null")
            }
        }
    }

    private fun updateUserData() {
        header.findViewById<TextView>(R.id.drawer_layout_name).text = USER.name
        header.findViewById<TextView>(R.id.drawer_layout_phone).text = USER.phone
        header.findViewById<CircleImageView>(R.id.account_image).downloadAndSetImage(USER.photoUrl)
    }

}