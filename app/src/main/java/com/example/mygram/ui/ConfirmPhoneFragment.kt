package com.example.mygram.ui

import Const.TEST_TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mygram.databinding.FragmentConfirmPhoneBinding
import com.example.mygram.ui.activity.AunteficationActivity
import com.example.mygram.ui.activity.MainActivity
import com.example.mygram.utils.*
import com.google.firebase.auth.PhoneAuthProvider


class ConfirmPhoneFragment : Fragment() {

    private var _binding: FragmentConfirmPhoneBinding? = null
    private val binding get() =  _binding!!
    lateinit var id: String
    lateinit var phoneNumber: String

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

        val text = binding.inputCode.text.toString()
        if (text.length == 6){
            enterCode()
        }
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
                val user = hashMapOf(CHILD_ID to userId,
                    CHILD_PHONE to phoneNumber,
                    CHILD_USERNAME to userId
                    )

                databaseRefRoot.collection(NODE_USERS)
                    .document(UID)
                    .set(user)
                    .addOnSuccessListener {
                        Log.d(TEST_TAG, "user add to users")
                    }
                    .addOnFailureListener {
                        Log.w(TEST_TAG, "adding error ${it.message.toString()}")
                    }

                val intent = Intent(activity as AunteficationActivity, MainActivity::class.java)
                startActivity(intent)
            } else{
                Log.d(TEST_TAG, "404")
            }
        }
    }
}