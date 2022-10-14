package com.example.mygram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mygram.databinding.FragmentChangeBioBinding
import com.example.mygram.viewModel.ProfileViewModel


class ChangeBioFragment : Fragment() {

    //viewBinding
    private var _binding: FragmentChangeBioBinding? = null
    private val binding get() = _binding!!

    //navigation
    private var _navController: NavController? = null
    private val navController get() = _navController!!

    //viewModel
    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _navController = findNavController()
        // Inflate the layout for this fragment
        _binding = FragmentChangeBioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {
            confirmBio.setOnClickListener {
                val bio = binding.bio.text.toString()
                viewModel.updateBio(bio)
                navController.navigateUp()
            }
            materialToolbarBio.setNavigationOnClickListener {
                navController.navigateUp()
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _navController = null
        _binding = null
        super.onDestroyView()
    }

}