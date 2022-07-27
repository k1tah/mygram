package com.example.mygram.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.mygram.R
import com.example.mygram.databinding.FragmentFavoritesBinding
import com.example.mygram.viewModel.MessagesViewModel


class FavoritesFragment : Fragment() {
    //viewBinding
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    //viewModel
    private val viewModel: MessagesViewModel by viewModels()
    //navigation actions

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
    }
}