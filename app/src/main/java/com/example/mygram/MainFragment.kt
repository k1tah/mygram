package com.example.mygram

import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
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
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.example.mygram.activity.MainActivity
import com.example.mygram.activity.adapter.MsgAdapter
import com.example.mygram.databinding.FragmentMainFragmentBinding
import com.example.mygram.viewModel.ViewModel
import com.google.android.material.navigation.NavigationView


class MainFragment : Fragment() {

    class MainFragmentOnPressedCallBack(
        private val slidingPaneLayout: SlidingPaneLayout
    ): OnBackPressedCallback(slidingPaneLayout.isSlideable && slidingPaneLayout.isOpen),

        SlidingPaneLayout.PanelSlideListener{

        init {
            slidingPaneLayout.addPanelSlideListener(this)
        }

        override fun handleOnBackPressed() {
            slidingPaneLayout.closePane()
        }

        override fun onPanelSlide(panel: View, slideOffset: Float) {
            TODO("Not yet implemented")
        }

        override fun onPanelOpened(panel: View) {
            isEnabled = true
        }

        override fun onPanelClosed(panel: View) {
            isEnabled = false
        }

    }



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