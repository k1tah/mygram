package com.example.mygram.ui

//import com.example.mygram.ui.adapter.MessagesAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mygram.R
import com.example.mygram.databinding.FragmentMainFragmentBinding
import com.example.mygram.viewModel.ProfileViewModel


class MainFragment : Fragment() {
    
    //viewBinding
    private var _binding: FragmentMainFragmentBinding? = null
    private val binding get() = _binding!!

    //navigation
    private var _navController: NavController? = null
    private val navController get() = _navController!!

    //viewModel
    //private val messageViewModel: MessagesViewModel by viewModels()
    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModel.ProfileViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _navController = findNavController()
        _binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_main_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getUserFromFirebase()
        val navigationView = binding.navigationView
        val toolbar = binding.toolbar
        val drawerLayout = binding.drawerLayout

        //val recyclerView = binding.recyclerView
        //recyclerView.adapter = context?.let { MessagesAdapter(it) }

        toolbar.setNavigationOnClickListener {
            viewModel.getUserFromFirebase()
            drawerLayout.open()
        }

        navigationView.setupWithNavController(findNavController())
        navigationView.setNavigationItemSelectedListener{
           when(it.itemId){
               R.id.settings -> navController.navigate(R.id.accountInfoFragment)
               R.id.account_image -> navController.navigate(R.id.accountInfoFragment)
           }
           true
        }

    }

    override fun onDestroyView() {
        _navController = null
        _binding = null
        super.onDestroyView()
    }

}