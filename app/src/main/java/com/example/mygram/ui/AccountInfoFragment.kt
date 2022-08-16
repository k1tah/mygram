package com.example.mygram.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mygram.R
import com.example.mygram.databinding.FragmentAccountInfoBinding


class AccountInfoFragment : Fragment() {

    private var _binding: FragmentAccountInfoBinding? = null
    private val binding get() = _binding!!

    //navigation
    private var _navController: NavController? = null
    private val navController get() = _navController!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _navController = findNavController()
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_info, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            nameContainerProfile.setOnClickListener {
                navController.navigate(R.id.changeProfileNameFragment)
            }
            accountDescriptionProfileContainer.setOnClickListener {
                navController.navigate(R.id.changeBioFragment)
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

}