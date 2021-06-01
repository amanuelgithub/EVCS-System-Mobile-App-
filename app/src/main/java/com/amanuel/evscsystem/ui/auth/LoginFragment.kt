package com.amanuel.evscsystem.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.amanuel.evscsystem.databinding.FragmentLoginBinding
import com.amanuel.evscsystem.data.network.AuthApi
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.data.repository.AuthRepository
import com.amanuel.evscsystem.ui.base.BaseFragment
import com.amanuel.evscsystem.ui.enable
import com.amanuel.evscsystem.ui.handleApiError
import com.amanuel.evscsystem.ui.home.HomeActivity
import com.amanuel.evscsystem.ui.startNewActivity
import com.amanuel.evscsystem.ui.visible
import kotlinx.coroutines.launch


class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressbar.visible(false)
        binding.buttonLogin.enable(false)


        // controls any kind of live update made in the case of logging in
        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAuthToken(it.value.user.access_token!!) // !! make cause a null pointer exception
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it) { login() }
            }
        })


        // change the state of the login button based on the presence of texts
        binding.editTextTextPassword.addTextChangedListener {
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            binding.buttonLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        // handles what things to do when clicking the login button
        binding.buttonLogin.setOnClickListener {
            login()
        }

    }

    private fun login() {
        val email = binding.editTextTextEmailAddress.text.toString().trim()
        val password = binding.editTextTextPassword.text.toString().trim()
        //@todo add input validation
        viewModel.login(email, password)
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
            AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)


}