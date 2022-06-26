package com.example.mygram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mygram.databinding.FragmentFavoritesBinding
import com.example.mygram.viewModel.ViewModel


class FavoritesFragment : Fragment() {
    //viewBinding
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    //viewModel
    private val viewModel: ViewModel by viewModels()
    //navigation actions

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
    }
}