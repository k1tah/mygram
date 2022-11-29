package com.example.mygram.ui

import Const.TEST_TAG_DATA
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.mygram.APP_ACTIVITY
import com.example.mygram.R
import com.example.mygram.databinding.FragmentSingleChatBinding
import com.example.mygram.domain.Message
import com.example.mygram.repository.MessagesRepository
import com.example.mygram.ui.adapter.MessagesAdapter
import com.example.mygram.utils.*
import com.example.mygram.viewModel.MessagesViewModel
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView


class SingleChatFragment : Fragment() {

    private val collectionUsers = databaseRefRoot.collection(NODE_USERS)

    //viewBinding
    private var _binding: FragmentSingleChatBinding? = null
    private val binding get() = _binding!!
    //navController
    private var _navController: NavController? = null
    private val navController get() =  _navController!!
    //view Model
    private val messagesViewModel: MessagesViewModel by activityViewModels()
    //UID
    private lateinit var uid: String
    //recyclerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MessagesAdapter
    private val listMessages = mutableListOf<Message>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _navController = findNavController()
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_single_chat, container, false)

        //init recyclerView
        recyclerView = binding.chatRecyclerview
        adapter = MessagesAdapter()
        binding.chatRecyclerview.adapter = adapter

        // Inflate the layout for this fragment
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //get args from contacts fragment
        val args: SingleChatFragmentArgs by navArgs()
        uid = args.id
        getUserData()
        observeMessages(uid)

        binding.apply {
            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //TODO("Not yet implemented")
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //TODO("Not yet implemented")
                }
                override fun afterTextChanged(p0: Editable?) {
                    if (messageEdit.text?.isNotBlank() == true){
                        sendImageMessage.visibility = View.GONE
                        sendMessage.visibility = View.VISIBLE
                    }
                    else {
                        sendMessage.visibility = View.GONE
                    }
                }

            }

            messageEdit.addTextChangedListener(textWatcher)

            sendMessage.setOnClickListener {
                val message = Message(
                    text = messageEdit.text.toString(),
                    type = TYPE_TEXT,
                    from = UID,
                    count = listMessages.size.toLong()
                )
                messagesViewModel.sendMessage(message, uid, null)
                messageEdit.text?.clear()
                adapter.notifyDataSetChanged()
            }

            sendImageMessage.setOnClickListener {
                changePhotos()
            }

            chatToolbar.setNavigationOnClickListener {
                navController.navigateUp()
            }

        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        listMessages.clear()
        _navController = null
        _binding = null
        super.onDestroyView()
    }

    private fun getUserData(){
        binding.apply {
            collectionUsers.document(uid).get()
                .addOnSuccessListener {
                    toolbarInfo.chatAccountName.text = it.data?.get(CHILD_USERNAME) as String
                    toolbarInfo.chatAccountState.text = it.data?.get(CHILD_STATE) as String
                    toolbarInfo.toolbarImage.downloadAndSetImage(it.data?.get(CHILD_PHOTO) as String)
                }
                .addOnFailureListener { exception ->
                    Log.w(TEST_TAG_DATA, "get contact error: ${exception.message}")
                }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeMessages(uid: String){
        MessagesRepository.collectionMessages.document(UID).collection(uid)
            .addSnapshotListener { collection, error ->
                if (error != null){
                    Log.w(TEST_TAG_DATA, "Listen messages failed.", error)
                    return@addSnapshotListener
                }
                if (collection != null){
                    listMessages.clear()
                    collection.documents.forEach { document ->
                        val message = Message()
                        document.data?.forEach {
                            when (it.key){
                                CHILD_FROM -> {
                                    message.from = it.value.toString()
                                }
                                CHILD_TYPE -> {
                                    message.type = it.value.toString()
                                }
                                CHILD_TEXT -> {
                                    message.text = it.value.toString()
                                }
                                CHILD_DATETIME -> {
                                    message.timestamp = it?.value.toString()
                                }
                                CHILD_COUNT -> {
                                    message.count = it.value as Long
                                }
                                CHILD_PHOTO -> {
                                    message.photoUrl = it.value.toString()
                                }
                            }
                            if (!listMessages.contains(message)){
                                listMessages.add(message)
                            }
                        }
                    }
                    listMessages.sortBy { it.count }
                    listMessages.forEach {
                        Log.d(TEST_TAG_DATA, "${it.text}, ${it.count}")
                    }
                    adapter.submitList(listMessages)
                    recyclerView.smoothScrollToPosition(adapter.itemCount)
                    adapter.notifyDataSetChanged()
                } else {
                    Log.d(TEST_TAG_DATA, "Current data: null")
                }
            }
    }

    private fun changePhotos(){
        val intent = CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(600, 600)
            .setCropShape(CropImageView.CropShape.RECTANGLE)
            .getIntent(APP_ACTIVITY)
        resultLauncher.launch(intent)
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val uri = CropImage.getActivityResult(result.data).uri
            val message = Message(
                type = TYPE_PHOTO,
                from = UID,
                count = listMessages.size.toLong()
            )
            messagesViewModel.sendMessage(message, uid, uri)
        }
    }

}