package com.example.mygram.ui

import Const.TEST_TAG_DATA
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mygram.R
import com.example.mygram.databinding.FragmentContactsBinding
import com.example.mygram.ui.adapter.ContactsAdapter
import com.example.mygram.utils.User.LISTCONTACTS
import com.example.mygram.viewModel.ProfileViewModel


class ContactsFragment : Fragment() {
    //viewBinding
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    //navController
    private var _navController: NavController? = null
    private val navController get() = _navController!!
    //view Model
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _navController = findNavController()
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contacts, container, false)

        binding.lifecycleOwner = this
        binding.profileViewModel = profileViewModel
        binding.contactRecyclerView.adapter = ContactsAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        profileViewModel.getUserContacts()

        val toolbar = binding.materialToolbar
        toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }

        LISTCONTACTS.forEach {
            Log.d(TEST_TAG_DATA, it.uid)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _navController = null
        _binding = null
        super.onDestroyView()
    }
}