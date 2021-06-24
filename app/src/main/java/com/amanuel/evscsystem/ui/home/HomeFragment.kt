package com.amanuel.evscsystem.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.amanuel.evscsystem.MainActivity
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.data.network.UserApi
import com.amanuel.evscsystem.data.repository.UserRepository
import com.amanuel.evscsystem.data.responses.User
import com.amanuel.evscsystem.databinding.FragmentHomeBinding
import com.amanuel.evscsystem.ui.base.BaseFragment
import com.amanuel.evscsystem.ui.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, UserRepository>() {

    private lateinit var appConfiguration: AppBarConfiguration

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** start codes related to creating a custom toolbar */

//        val toolbar = binding.toolbarHome
//        toolbar.inflateMenu(R.menu.custom_home_fragment_menu)
//
//        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24)
//        toolbar.setNavigationOnClickListener { view ->
//            // reference to drawer
//        }

//        handleOnClickOnCustomOptions()

        // add the navigation icon

        /** end codes related to creating a custom toolbar */


//        binding.progressbarHome.visible(false)
//
//        viewModel.getUser()
//
//        viewModel.user.observe(viewLifecycleOwner, Observer {
//            when (it) {
//                is Resource.Success -> {
//                    binding.progressbarHome.visible(false)
//                    updateUI(it.value.user)
//                }
//                is Resource.Loading -> {
//                    binding.progressbarHome.visible(true)
//                }
//            }
//        })
//
//
//        binding.buttonLogout.setOnClickListener {
//            logout()
//        }

    }



    private fun updateUI(user: User) {
//        with(binding) {
//            textViewId.text = user.pk.toString()
//            textViewName.text = user.username
//            textViewEmail.text = user.email
//        }
    }


    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        // runBlocking function stops other tasks.so is good if not used.
        // to quickly find the token request the token at the start of the fragment(called on the BaseFragment)
        val token = runBlocking { userPreferences.authToken.first() }
        val api = remoteDataSource.buildApi(UserApi::class.java, token)

        return UserRepository(api)
    }

}