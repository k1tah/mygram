package com.example.mygram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mygram.databinding.FragmentNewGroupBinding
import com.example.mygram.viewModel.ViewModel


class NewGroupFragment : Fragment() {
    //binding
    private var _binding: FragmentNewGroupBinding? = null
    private val binding get() =  _binding!!
    //viewModel
    private val viewModel: ViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_group, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = binding.toolbarNewGroup

        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_newGroupFragment_to_mainFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroy()
        _binding = null
    }
}