package com.example.mygram.ui

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mygram.MyGrammApplication
import com.example.mygram.R
import com.example.mygram.databinding.FragmentMainFragmentBinding
import com.example.mygram.domain.Message
//import com.example.mygram.ui.adapter.MessagesAdapter
import com.example.mygram.viewModel.MessagesViewModel
import com.example.mygram.viewModel.ProfileViewModel


class MainFragment : Fragment() {
    
    //viewBinding
    private var _binding: FragmentMainFragmentBinding? = null
    private val binding get() = _binding!!
    //viewModel
    private val messageViewModel: MessagesViewModel by viewModels()
    //private val viewModel: ProfileViewModel by viewModels()
    /*
    private val ViewModel: ProfileViewModel by viewModels {
        ProfileViewModel.ProfileViewModelFactory(
            (activity?.application as MyGrammApplication).database.profileDao()
        )
    }
    */
    //navigation actions
    private val action = MainFragmentDirections.actionMainFragmentToAccountInfoFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_main_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navigationView = binding.navigationView
        val toolbar = binding.toolbar
        val drawerLayout = binding.drawerLayout
        //val recyclerView = binding.recyclerView

        //recyclerView.adapter = context?.let { MessagesAdapter(it) }

        toolbar.setNavigationOnClickListener {
            drawerLayout.open()
        }

        navigationView.setupWithNavController(findNavController())
        navigationView.setNavigationItemSelectedListener{
           when(it.itemId){
               R.id.settings -> findNavController().navigate(R.id.accountInfoFragment)
               R.id.account_image -> findNavController().navigate(R.id.accountInfoFragment)
           }
           true
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}