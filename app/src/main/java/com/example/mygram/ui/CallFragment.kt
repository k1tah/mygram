package com.example.mygram.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mygram.R
import com.example.mygram.databinding.FragmentCallBinding


class CallFragment : Fragment() {
    //viewBinding
    private var _binding: FragmentCallBinding? = null
    private val binding get() = _binding!!
    //viewModel

    //navigation actions

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_call, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}