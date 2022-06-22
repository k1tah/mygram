package com.example.mygram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mygram.activity.adapter.MsgAdapter
import com.example.mygram.databinding.FragmentMainFragmentBinding


class MainFragment : Fragment() {
    var _binding: FragmentMainFragmentBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recyclerView = binding.recyclerView
        recyclerView.adapter = context?.let { MsgAdapter(it) }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }






}