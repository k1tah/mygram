package com.example.mygram

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mygram.adapter.MsgAdapter
import com.example.mygram.databinding.FragmentMainFragmentBinding
import com.example.mygram.viewModel.ViewModel


class MainFragment : Fragment() {
    
    //viewBinding
    private var _binding: FragmentMainFragmentBinding? = null
    private val binding get() = _binding!!
    //viewModel
    private val viewModel: ViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_fragment, container, false)
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
        }

        navigationView.setupWithNavController(findNavController())
        navigationView.setNavigationItemSelectedListener{
           when(it.itemId){
               R.id.settings -> navigation(R.id.fragment_settings)

               R.id.new_group -> navigation(R.id.newGroupFragment)
           }
           true
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun navigation(resId: Int){
        findNavController().navigate(resId)
    }
}