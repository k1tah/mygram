package com.example.mygram.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mygram.R
import com.example.mygram.databinding.FragmentConfirmPhoneBinding


class ConfirmPhoneFragment : Fragment() {

    private var _binding: FragmentConfirmPhoneBinding? = null
    private val binding get() =  _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConfirmPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }
}