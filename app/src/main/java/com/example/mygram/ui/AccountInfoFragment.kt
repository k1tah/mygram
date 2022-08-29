package com.example.mygram.ui

import Const.TEST_TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mygram.R
import com.example.mygram.databinding.FragmentAccountInfoBinding
import com.example.mygram.ui.activity.AunteficationActivity
import com.example.mygram.ui.activity.MainActivity
import com.example.mygram.utils.auth
import com.example.mygram.viewModel.ProfileViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider


class AccountInfoFragment : Fragment() {
    private lateinit var code: String
    private lateinit var callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    //dataBinding
    private var _binding: FragmentAccountInfoBinding? = null
    private val binding get() = _binding!!
    //navigation
    private var _navController: NavController? = null
    private val navController get() = _navController!!
    //viewModel
    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModel.ProfileViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _navController = findNavController()
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_info, container, false)
        return  binding.root
    }

    override fun onStart() {
        callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                auth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d(TEST_TAG,"sucs delete")
                    }
                }
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                Log.d(TEST_TAG, "${exception.message}")
            }

        }
        super.onStart()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            nameContainerProfile.setOnClickListener {
                navController.navigate(R.id.changeProfileNameFragment)
            }
            accountDescriptionProfileContainer.setOnClickListener {
                navController.navigate(R.id.changeBioFragment)
            }
            signOutButton.setOnClickListener {
                Log.d(TEST_TAG, "sign out")
                viewModel.signOut()
                val intent = Intent(activity, AunteficationActivity::class.java)
                startActivity(intent)
            }

            code = codeInput.text.toString()

            deleteAccountButton.setOnClickListener {
                confirmCodeLayout.visibility = View.VISIBLE
            }

            confirmCodeButton.setOnClickListener {
                viewModel.deleteAccount(code, activity as MainActivity, callback)
            }

        }

        super.onViewCreated(view, savedInstanceState)
    }

}