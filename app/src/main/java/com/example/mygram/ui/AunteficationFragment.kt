package com.example.mygram.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.navigation.fragment.findNavController
import com.example.mygram.R
import com.example.mygram.databinding.FragmentAunteficationBinding
import com.example.mygram.ui.activity.AunteficationActivity
import com.example.mygram.ui.activity.MainActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class AunteficationFragment : Fragment() {
    //binding
    private var _binding: FragmentAunteficationBinding? = null
    private val binding get() = _binding!!

    private lateinit var phoneNumber: String
    private lateinit var callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAunteficationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.okRegButton.setOnClickListener {
            isNumberCorrect()
        }
    }

    override fun onStart() {
        auth = FirebaseAuth.getInstance()
        callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                auth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful){
                        val intent = Intent(activity as AunteficationActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {

            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                val direction = AunteficationFragmentDirections.actionAunteficationFragmentToConfirmPhoneFragment(phoneNumber, id)
                findNavController().navigate(direction)
            }

        }
        super.onStart()
    }

    private fun isNumberCorrect(){
        val numberInput = binding.inputNumber
        if (numberInput.text.isNullOrEmpty()){
            numberInput.setError("Uncorrect number", R.drawable.ic_baseline_error_24.toDrawable())
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