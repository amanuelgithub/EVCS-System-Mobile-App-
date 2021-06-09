package com.amanuel.evscsystem.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.amanuel.evscsystem.data.UserPreferences
import com.amanuel.evscsystem.data.network.RemoteDataSource
import com.amanuel.evscsystem.data.network.UserApi
import com.amanuel.evscsystem.data.repository.BaseRepository
import com.amanuel.evscsystem.ui.auth.AuthActivity
import com.amanuel.evscsystem.ui.startNewActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


// assuming that all fragment need a viewModel, viewBinding, and a repository this
// abs class serve as a base class

abstract class BaseFragment<VM : BaseViewModel, B : ViewBinding, R : BaseRepository> : Fragment() {

    protected lateinit var userPreferences: UserPreferences

    protected lateinit var binding: B
    protected lateinit var viewModel: VM

    protected val remoteDataSource = RemoteDataSource()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userPreferences = UserPreferences(requireContext())
        binding = getFragmentBinding(inflater, container)
        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())

        // helps to make accessing the authToken easy at later times, forexample
        // in the time of call "runBlocking" function in the HomeFragment
        lifecycleScope.launch { userPreferences.authToken.first() }

        return binding.root
    }


    fun logout() = lifecycleScope.launch {
        val authToken = userPreferences.authToken.first()
        val api = remoteDataSource.buildApi(UserApi::class.java, authToken)
        viewModel.logout(api)
        // clear the local storage
        userPreferences.clear()
        requireActivity().startNewActivity(AuthActivity::class.java)
    }

    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    abstract fun getFragmentRepository(): R

}