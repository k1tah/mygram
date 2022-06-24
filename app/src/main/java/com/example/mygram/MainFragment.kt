package com.example.mygram

import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mygram.activity.MainActivity
import com.example.mygram.activity.adapter.MsgAdapter
import com.example.mygram.databinding.FragmentMainFragmentBinding
import com.example.mygram.viewModel.ViewModel
import com.google.android.material.navigation.NavigationView


class MainFragment : Fragment() {
    //viewBinding
    private var _binding: FragmentMainFragmentBinding? = null
    private val binding get() = _binding!!
    //viewModel
    private val viewModel: ViewModel by viewModels()
    //navigation actions
    private val settingsNavigate = MainFragmentDirections.actionMainFragmentToFragmentSettings()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navigationView = binding.navigationView
        val toolbar = binding.toolbar
        val drawerLayout = binding.drawerLayout
        val recyclerView = binding.recyclerView

        recyclerView.adapter = context?.let { MsgAdapter(it) }

        toolbar.setNavigationOnClickListener {
            drawerLayout.open()
            Log.d(Const.testTag, "test")
        }
        navigationView.setupWithNavController(findNavController())
        navigationView.setNavigationItemSelectedListener{
           when(it.itemId){
               R.id.settings -> navigation(settingsNavigate)
           }
           true
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun navigation(direction: NavDirections){
        findNavController().navigate(direction)
    }

}