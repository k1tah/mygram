package com.example.mygram.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mygram.R
import com.example.mygram.databinding.FragmentChangeProfileNameBinding
import com.example.mygram.viewModel.ProfileViewModel

class ChangeProfileNameFragment : Fragment() {

    private var _binding: FragmentChangeProfileNameBinding? = null
    private val binding get() =  _binding!!

    //navigation
    private var _navController: NavController? = null
    private val navController get() = _navController!!

    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _navController = findNavController()
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_change_profile_name, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = binding.toolbarChangeName
        toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }

        binding.profileSaveNameButton.setOnClickListener {
            val name = binding.profileEditName.text.toString()
            viewModel.updateUserName(name)
            navController.navigateUp()
        }
    }

    override fun onDestroyView() {
        _navController = null
        _binding = null
        super.onDestroyView()
    }

}