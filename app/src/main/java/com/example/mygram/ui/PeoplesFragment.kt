package com.example.mygram.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.mygram.R
import com.example.mygram.databinding.FragmentPeoplesBinding


class PeoplesFragment : Fragment() {
    //viewBinding
    private var _binding: FragmentPeoplesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_peoples, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
    }
}