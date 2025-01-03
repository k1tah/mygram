package com.example.mygram.presentation.activity.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mygram.R
import com.example.mygram.data.repository.auth
import com.example.mygram.databinding.FragmentAunteficationBinding
import com.example.mygram.presentation.activity.AunteficationActivity
import com.example.mygram.presentation.activity.MainActivity
import com.example.mygram.utils.TestTags.TEST_TAG_AUTH
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class AunteficationFragment : Fragment() {
    //binding
    private var _binding: FragmentAunteficationBinding? = null
    private val binding get() = _binding!!

    private lateinit var phoneNumber: String
    private lateinit var callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAunteficationBinding.inflate(inflater, container, false)
        binding.inputNumber.setText(R.string.rus_number)
        return binding.root
    }

    override fun onStart() {
        callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                auth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful){
                        val intent = Intent(activity as AunteficationActivity, MainActivity::class.java)
                        startActivity(intent)
                        (activity as AunteficationActivity).finish()
                    }
                }
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                exception.message?.let { Log.d(TEST_TAG_AUTH, it) }
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                val action = AunteficationFragmentDirections.actionAunteficationFragmentToConfirmPhoneFragment(id, phoneNumber)
                findNavController().navigate(action)
            }
        }
        binding.okRegButton.setOnClickListener { isNumberCorrect() }
        super.onStart()
    }

    private fun isNumberCorrect(){
        val numberInput = binding.inputNumber
        if (numberInput.text.isNullOrEmpty()){
            numberInput.setError("Uncorrected number", R.drawable.ic_baseline_error_24.toDrawable())
        } else{
            authUser()
        }
    }

    private fun authUser(){
        phoneNumber = binding.inputNumber.text.toString()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            activity as AunteficationActivity,
            callback
        )
    }

}