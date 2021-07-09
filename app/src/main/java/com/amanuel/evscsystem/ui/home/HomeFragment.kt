package com.amanuel.evscsystem.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.ui.AppBarConfiguration
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.data.UserPreferences
import com.amanuel.evscsystem.data.network.RemoteServiceBuilderHelper
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    private lateinit var appConfiguration: AppBarConfiguration

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)


//        viewModel.getUser()

//        viewModel.user.observe(viewLifecycleOwner, Observer { resources ->
//            when (resources) {
//                is Resource.Success -> {
//                    Toast.makeText(requireActivity(), "${resources.data?.user}", Toast.LENGTH_SHORT)
//                        .show()
//                }
//                is Resource.Loading -> {
//                    // show progress bar or something
//                }
//                is Resource.Failure -> {
//                    Toast.makeText(
//                        requireActivity(),
//                        resources.error.toString(),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        })


//        binding.buttonLogout.setOnClickListener {
//            // todo: logout imp
//        }

    }


}