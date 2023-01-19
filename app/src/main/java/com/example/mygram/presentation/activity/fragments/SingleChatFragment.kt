package com.example.mygram.presentation.activity.fragments

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
import com.example.mygram.data.repository.*
import com.example.mygram.data.repository.MessagesRepository.isCheckedChat
import com.example.mygram.data.repository.MessagesRepository.saveToMainList
import com.example.mygram.data.repository.ProfileRepository.collectionUsers
import com.example.mygram.data.repository.viewModel.MessagesViewModel
import com.example.mygram.databinding.FragmentSingleChatBinding
import com.example.mygram.domain.Chat
import com.example.mygram.domain.Message
import com.example.mygram.presentation.activity.recyclerView.adapter.MessagesAdapter
import com.example.mygram.utils.*
import com.example.mygram.utils.TestTags.TEST_TAG_DATA
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView


class SingleChatFragment : Fragment() {

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
    private lateinit var user: com.example.mygram.domain.User
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

    @SuppressLint("NotifyDataSetChanged", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //get args from contacts fragment
        val args: SingleChatFragmentArgs by navArgs()
        uid = args.id
        user = com.example.mygram.domain.User()
        getUserData()
        observeMessages(uid)
        isCheckedChat(UID, uid)

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
                    from = UID
                )
                messagesViewModel.sendMessage(message, uid)
                messageEdit.text?.clear()
                adapter.notifyDataSetChanged()
                saveToMainList(
                    Chat(
                        id = uid,
                        type = TYPE_CHAT,
                        lastMessage = message.text,
                        name = user.name,
                        imageUrl = user.photoUrl,
                        messageType = message.type,
                        from = UID
                    )
                )
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
                    user.name = it.data?.get(CHILD_USERNAME) as String
                    user.photoUrl = it.data?.get(CHILD_PHOTO) as String
                    user.state = it.data?.get(CHILD_STATE) as String
                    binding.apply {
                        toolbarInfo.apply {
                            chatAccountName.text = user.name
                            chatAccountState.text = user.state
                            toolbarImage.downloadAndSetImage(user.photoUrl)
                        }
                    }
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
                                CHILD_PHOTO -> {
                                    message.url = it.value.toString()
                                }
                            }
                            if (!listMessages.contains(message)){
                                listMessages.add(message)
                            }
                        }
                    }
                    listMessages.sortBy { it.timestamp.toInt() }
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
            val messageKey = "image ${listMessages.size.toLong()}"
            val message = Message(
                type = TYPE_PHOTO,
                from = UID,
                messageKey = messageKey
            )
            messagesViewModel.sendFile(message, uid, uri)
        }
    }

}