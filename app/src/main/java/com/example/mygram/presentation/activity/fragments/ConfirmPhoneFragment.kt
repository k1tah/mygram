package com.example.mygram.presentation.activity.fragments

import com.example.mygram.utils.TestTags.TEST_TAG_AUTH
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.mygram.data.repository.*
import com.example.mygram.data.repository.viewModel.ProfileViewModel
import com.example.mygram.databinding.FragmentConfirmPhoneBinding
import com.example.mygram.presentation.activity.AunteficationActivity
import com.example.mygram.presentation.activity.MainActivity
import com.example.mygram.utils.AppStates
import com.google.firebase.auth.PhoneAuthProvider


class ConfirmPhoneFragment : Fragment() {

    private var _binding: FragmentConfirmPhoneBinding? = null
    private val binding get() =  _binding!!
    lateinit var id: String
    lateinit var phoneNumber: String

    val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //get args from first fragment
        val args: ConfirmPhoneFragmentArgs by navArgs()
        id = args.id
        phoneNumber = args.phoneNumber

        val textWatcher = object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (binding.inputCode.text?.length == 6){
                    enterCode()
                }
            }

        }

        binding.inputCode.addTextChangedListener(textWatcher)

        binding.confirmButton.setOnClickListener {
            enterCode()
        }
        super.onViewCreated(view, savedInstanceState)
    }


    private fun enterCode(){
        val code = binding.inputCode.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                val userId = auth.currentUser?.uid.toString()
                val user = hashMapOf<String, Any>(
                    CHILD_ID to userId,
                    CHILD_PHONE to phoneNumber,
                    CHILD_USERNAME to userId,
                    CHILD_BIO to "",
                    CHILD_PHOTO to "",
                    CHILD_STATE to AppStates.OFFLINE
                    )
                val userPhone = hashMapOf(
                    userId to phoneNumber
                )
                viewModel.addUser(user, userPhone)
                val intent = Intent(activity as AunteficationActivity, MainActivity::class.java)
                startActivity(intent)
                (activity as AunteficationActivity).finish()
            } else{
                Log.d(TEST_TAG_AUTH, "404")
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}