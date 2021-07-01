package com.amanuel.evscsystem.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.databinding.FragmentLoginBinding
import com.amanuel.evscsystem.ui.enable
import com.amanuel.evscsystem.ui.handleApiError
import com.amanuel.evscsystem.ui.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    // Note: injected using hilt
    private val viewModel: AuthViewModel by viewModels()

    private lateinit var binding: FragmentLoginBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)

        // move to the forgetPassword_fragment
        binding.textViewTextForgetPassword.setOnClickListener { v: View ->
            navToForgetPasswordFragment(v)
        }

        binding.progressbar.visible(false)
        binding.buttonLogin.enable(false)


        // change the state of the login button based on the presence of texts
        binding.editTextTextPassword.addTextChangedListener {
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            binding.buttonLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }


        // controls any kind of live update made in the case of logging in
        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
//                        viewModel.saveAuthToken(it.value.user.access_token!!) // !! make cause a null pointer exception

                        viewModel.saveAuthToken(it.data?.key!!) // !! make cause a null pointer exception
                        //@todo 1: saveFCMTokenTo remote server
//                        if (it.value.user.fcm_token.isNullOrEmpty()){
//
//                        }
                        //@todo 2: check the traffic police selected a location: and based on that move either to fragment_home.xml or to fragment_location_config.xml
                        //@todo 3: also replace the below code with one that will work with fragment
//                        requireActivity().startNewActivity(HomeActivity::class.java)



                    }
                }
                is Resource.Failure -> handleApiError(it) { login() }
            }
        })

        // handles what things to do when clicking the login button
        binding.buttonLogin.setOnClickListener {
//            login()
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }


    private fun navToForgetPasswordFragment(view: View) {
        view.findNavController().navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
    }

    private fun login() {
        val email = binding.editTextTextEmailAddress.text.toString().trim()
        val password = binding.editTextTextPassword.text.toString().trim()
        //@todo add input validation
        viewModel.login(email, password)
    }

}


