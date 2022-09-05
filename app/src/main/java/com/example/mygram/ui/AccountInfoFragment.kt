package com.example.mygram.ui

import Const.TEST_TAG
import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mygram.BuildConfig
import com.example.mygram.R
import com.example.mygram.databinding.FragmentAccountInfoBinding
import com.example.mygram.ui.activity.AunteficationActivity
import com.example.mygram.ui.activity.MainActivity
import com.example.mygram.utils.USER
import com.example.mygram.utils.auth
import com.example.mygram.viewModel.ProfileViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.io.File


class AccountInfoFragment : Fragment() {
    //photo
    private lateinit var imageView: ImageView
    private lateinit var file: File
    private lateinit var uri: Uri
    private lateinit var cameraIntent: Intent
    private lateinit var galleryIntent: Intent
    private lateinit var cropIntent: Intent

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
        enableRuntimePermission()
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
                Log.d(TEST_TAG, "sign out")
                viewModel.signOut()
                val intent = Intent(activity, AunteficationActivity::class.java)
                startActivity(intent)
            }
            //delete account
            code = codeInput.text.toString()
            deleteAccountButton.setOnClickListener {
                confirmCodeLayout.visibility = View.VISIBLE
            }
            confirmCodeButton.setOnClickListener {
                viewModel.deleteAccount(code, activity as MainActivity, callback)
            }
            //set profile photo
            imageView = accountImageProfile
            addPhoto.setOnClickListener {
                createDialogWindow()
            }
            //text fields
            accountNumberProfile.text = USER.phone
            accountDescriptionProfile.text = USER.bio
            accountNameProfile.text = USER.name
            accountNameProfileTop.text = USER.name
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun createDialogWindow() {
        val dialog = MaterialAlertDialogBuilder(activity as MainActivity)
        dialog.apply {
            setTitle(R.string.photoDialogTitle)
            setNeutralButton(R.string.photoDialogTitleCamera){ dialog, _ ->
                openCamera()
                dialog.dismiss()
            }
            setPositiveButton(R.string.photoDialogTitleGallery){ dialog, _ ->
                openGallery()
                dialog.dismiss()
            }
            setNegativeButton(R.string.photoDialogTitleClose){ dialog, _ ->
                dialog.dismiss()
            }
        }
        dialog.create()
        dialog.show()
    }

    private fun openGallery() {
        galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(Intent.createChooser(
            galleryIntent,
            R.string.photoDialogTitlePickImage.toString()), 2
        )
    }

    private fun openCamera() {
        cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        file = File(Environment.getExternalStorageDirectory(),
            "file${System.currentTimeMillis()}.jpg"
        )
        uri = FileProvider.getUriForFile(activity as MainActivity, BuildConfig.APPLICATION_ID +".provider", file)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        cameraIntent.putExtra("return_data", true)
        startActivityForResult(cameraIntent, 1)
    }

    private fun enableRuntimePermission(){
        activity?.let {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity as MainActivity, Manifest.permission.CAMERA)){
                Log.d(TEST_TAG, "ok permission")
            } else{
                ActivityCompat.requestPermissions(activity as MainActivity,
                arrayOf(Manifest.permission.CAMERA), RequestPermissionCode)
            }
        }
    }

    private fun cropImages(){
        try {
            cropIntent = Intent("com.android.camera.action.CROP")
            cropIntent.setDataAndType(uri, "image/*")
            cropIntent.putExtra("crop", true)
            cropIntent.putExtra("outputX", 180)
            cropIntent.putExtra("outputY", 180)
            cropIntent.putExtra("aspectX", 3)
            cropIntent.putExtra("aspectY", 4)
            cropIntent.putExtra("scaleUpIfNeeded", true)
            cropIntent.putExtra("return_data", true)
            startActivityForResult(cropIntent, 3)
        } catch (exception: ActivityNotFoundException){
            exception.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode){
            RequestPermissionCode -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d(TEST_TAG, "permission granted")
            } else {
                Log.d(TEST_TAG, "permission not granted")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            if (data != null) {
                uri = data.data!!
                cropImages()
            }
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK){
            if (data != null){
                uri = data.data!!
                cropImages()
            }
        } else if (requestCode == 3) {
            if (data != null){
                viewModel.updateUserPhoto(uri)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    companion object{
        const val RequestPermissionCode = 111
    }
}