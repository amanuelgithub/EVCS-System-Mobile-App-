package com.amanuel.evscsystem.ui.base

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.amanuel.evscsystem.data.UserPreferences
import com.amanuel.evscsystem.data.network.RemoteServiceBuilderHelper
import com.amanuel.evscsystem.data.network.UserApi
import com.amanuel.evscsystem.data.repository.BaseRepository
import com.amanuel.evscsystem.data.responses.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


// assuming that all fragment need a viewModel, viewBinding, and a repository this
// abs class serve as a base class

abstract class BaseFragment(contentLayoutId: Int) : Fragment() {

    var userPreferences: UserPreferences = UserPreferences(requireContext())
//    lateinit var binding: ViewDataBinding

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
////        return binding.root
//    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
////        val viewRoot = LayoutInflater.from(requireContext()).inflate(contentLayoutId, container, false)
//
//
//        userPreferences = UserPreferences(requireContext())
////        binding = getFragmentBinding(inflater, container)
////        val factory = ViewModelFactory(getFragmentRepository())
////        viewModel = ViewModelProvider(this, factory).get(getViewModel())
//
//        // helps to make accessing the authToken easy at later times, forexample
//        // in the time of call "runBlocking" function in the HomeFragment
//        lifecycleScope.launch { userPreferences.authToken.first() }
////
////        return binding.root
//    }


    fun logout() = lifecycleScope.launch {
        val authToken = userPreferences.authToken.first()
//        val api = remoteDataSource.buildApi(UserApi::class.java, authToken)
//        viewModel.logout(api)
        // clear the local storage
        userPreferences.clear()
         // @todo: [replace with LoginFragment] -> clear the BackStack to move to the specified fragment
//        requireActivity().startNewActivity(AuthActivity::class.java)
    }


}