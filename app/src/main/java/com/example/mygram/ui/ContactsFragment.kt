package com.example.mygram.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.mygram.R
import com.example.mygram.databinding.FragmentContactsBinding


class ContactsFragment : Fragment() {
    //viewBinding
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    //navController
    private var _navController: NavController? = null
    private val navController get() = _navController
    //view Model


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contacts, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}