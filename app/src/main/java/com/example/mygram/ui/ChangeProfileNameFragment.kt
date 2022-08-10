package com.example.mygram.ui

import Const.TEST_TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.mygram.MyGrammApplication
import com.example.mygram.R
import com.example.mygram.databinding.FragmentChangeProfileNameBinding
import com.example.mygram.utils.*
import com.example.mygram.viewModel.ProfileViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ChangeProfileNameFragment : Fragment() {

    private var _binding: FragmentChangeProfileNameBinding? = null
    private val binding get() =  _binding!!

    //private val viewModel: ProfileViewModel by viewModels()
    /*
    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModel.ProfileViewModelFactory(
            (activity?.application as MyGrammApplication).database.profileDao()
        )
    }
    */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_change_profile_name, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val action = ChangeProfileNameFragmentDirections.actionChangeProfileNameFragmentToAccountInfoFragment()

        val toolbar = binding.toolbarChangeName
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(action)
        }

        binding.profileSaveNameButton.setOnClickListener {
            val name = binding.profileEditName.text.toString()
            Log.d(TEST_TAG, name)
            //viewModel.updateUserName(name)
            updateUserName(name)
            findNavController().navigate(action)
        }
    }

    private fun updateUserName(name: String){
            databaseRefRoot.collection(NODE_USERS).document(UID).update(CHILD_USERNAME, name)
                .addOnSuccessListener {
                    Log.d(TEST_TAG, "update sucs")
                }
                .addOnFailureListener {
                    Log.d(TEST_TAG, "${it.message} pizdec")
                }
    }

}