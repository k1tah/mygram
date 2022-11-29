package com.example.mygram.ui

//import com.example.mygram.ui.adapter.MessagesAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mygram.R
import com.example.mygram.databinding.FragmentMainFragmentBinding
import com.example.mygram.utils.User.USER
import com.example.mygram.utils.downloadAndSetImage
import com.example.mygram.viewModel.ProfileViewModel
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView


class MainFragment : Fragment() {
    
    //viewBinding
    private var _binding: FragmentMainFragmentBinding? = null
    private val binding get() = _binding!!

    //navigation
    private var _navController: NavController? = null
    private val navController get() = _navController!!

    //viewModel
    //private val messageViewModel: MessagesViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()

    //navView
    private lateinit var navigationView: NavigationView
    private lateinit var header: View

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

        val owner = viewLifecycleOwner
        profileViewModel.user.observe(owner){
            header.findViewById<TextView>(R.id.drawer_layout_name).text = it.name
            header.findViewById<TextView>(R.id.drawer_layout_phone).text = it.phone
            header.findViewById<CircleImageView>(R.id.account_image).downloadAndSetImage(it.photoUrl)
        }

        navigationView = binding.navigationView
        header = navigationView.getHeaderView(0
        )

        val accountImage = header.findViewById<CircleImageView>(R.id.account_image)
        val toolbar = binding.toolbar
        val drawerLayout = binding.drawerLayout

        //val recyclerView = binding.recyclerView
        //recyclerView.adapter = context?.let { MessagesAdapter(it) }

        toolbar.setNavigationOnClickListener {
            updateUserData()
            drawerLayout.open()
        }

        accountImage.setOnClickListener {
            navController.navigate(R.id.accountInfoFragment)
            drawerLayout.close()
        }

        navigationView.setupWithNavController(navController)
        navigationView.setNavigationItemSelectedListener{
           when(it.itemId){
               R.id.settings -> {
                   navController.navigate(R.id.accountInfoFragment)
                   drawerLayout.close()
               }
               R.id.contacts -> {
                   navController.navigate(R.id.contactsFragment)
                   drawerLayout.close()
               }
           }
           true
        }

    }

    override fun onDestroyView() {
        _navController = null
        _binding = null
        super.onDestroyView()
    }

    private fun updateUserData(){
        header.findViewById<TextView>(R.id.drawer_layout_name).text = USER.name
        header.findViewById<TextView>(R.id.drawer_layout_phone).text = USER.phone
        header.findViewById<CircleImageView>(R.id.account_image).downloadAndSetImage(USER.photoUrl)
    }

}