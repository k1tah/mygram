package com.example.mygram.presentation.activity.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import com.example.mygram.APP_ACTIVITY
import com.example.mygram.R
import com.example.mygram.data.repository.auth
import com.example.mygram.data.repository.viewModel.ProfileViewModel
import com.example.mygram.databinding.FragmentAccountInfoBinding
import com.example.mygram.presentation.activity.AunteficationActivity
import com.example.mygram.utils.AppStates
import com.example.mygram.utils.TestTags.TEST_TAG
import com.example.mygram.utils.downloadAndSetImage
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView


class SettingsFragment : Fragment() {
    private lateinit var code: String
    private lateinit var callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    //dataBinding
    private var _binding: FragmentAccountInfoBinding? = null
    private val binding get() = _binding!!
    //navigation
    private var _navController: NavController? = null
    private val navController get() = _navController!!
    //viewModel
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //navController
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
                        Log.d(TEST_TAG,"sus delete")
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
        profileViewModel.getUser()
        val owner = viewLifecycleOwner
        profileViewModel.user.observe(owner){
            binding.apply {
                accountNameProfile.text = it.name
                accountNameProfileTop.text = it.name
                accountDescriptionProfile.text = it.bio
                accountNumberProfile.text = it.phone
                accountImageProfile.downloadAndSetImage(it.photoUrl)
            }
        }

        binding.apply {
            toolbarSettings.setNavigationOnClickListener {
                navController.navigateUp()
            }
            //set name
            nameContainerProfile.setOnClickListener {
                navController.navigate(R.id.changeProfileNameFragment)
            }
            //set bio
            accountDescriptionProfileContainer.setOnClickListener {
                navController.navigate(R.id.changeBioFragment)
            }
            //sign out
            signOutButton.setOnClickListener {
                profileViewModel.updateState(AppStates.OFFLINE)
                profileViewModel.signOut()
                val intent = Intent(activity, AunteficationActivity::class.java)
                startActivity(intent)
            }
            //set profile photo
            addPhoto.setOnClickListener {
                changeUserPhoto()
            }

        }
        super.onViewCreated(view, savedInstanceState)
    }


    private fun changeUserPhoto(){
        val intent = CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(600, 600)
            .setCropShape(CropImageView.CropShape.OVAL)
            .getIntent(APP_ACTIVITY)
        resultLauncher.launch(intent)
    }
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val uri = CropImage.getActivityResult(result.data).uri
            profileViewModel.updateUserPhoto(uri)
            navController.navigateUp()
        }
    }



}