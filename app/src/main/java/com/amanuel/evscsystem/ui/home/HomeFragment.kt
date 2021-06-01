package com.amanuel.evscsystem.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.amanuel.evscsystem.databinding.FragmentHomeBinding
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.data.network.UserApi
import com.amanuel.evscsystem.data.repository.UserRepository
import com.amanuel.evscsystem.data.responses.User
import com.amanuel.evscsystem.ui.base.BaseFragment
import com.amanuel.evscsystem.ui.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, UserRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressbarHome.visible(false)

        viewModel.getUser()

        viewModel.user.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.progressbarHome.visible(false)
                    updateUI(it.value.user)
                }
                is Resource.Loading -> {
                    binding.progressbarHome.visible(true)
                }
            }
        })


        binding.buttonLogout.setOnClickListener {
            logout()
        }

    }

    private fun updateUI(user: User) {
        with(binding) {
            textViewId.text = user.id.toString()
            textViewName.text = user.name
            textViewEmail.text = user.email
        }
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