package com.amanuel.evscsystem.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.connectivity.Connectivity
import com.amanuel.evscsystem.data.SessionManager
import com.amanuel.evscsystem.data.db.models.User
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.databinding.FragmentLoginBinding
import com.amanuel.evscsystem.ui.enable
import com.amanuel.evscsystem.ui.handleApiError
import com.amanuel.evscsystem.ui.visible
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    companion object {
        private const val TAG = "LoginFragment"
    }

    private val viewModel: AuthViewModel by viewModels()

    //    private lateinit var preferences: UserPreferences
    @Inject
    lateinit var sessionManager: SessionManager

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)

//        preferences = UserPreferences(requireContext())
        sessionManager = SessionManager(requireContext())

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

        /**
        viewModel.loginResponse.observe(viewLifecycleOwner) { resource ->
        //            Snackbar.make(requireView(), "Login response", Snackbar.LENGTH_LONG).show()
        //            binding.progressbar.visible(resource is Resource.Loading)
        when {
        resource is Resource.Success && (resource.data != null) -> {
        Log.d(TAG, "onViewCreated: ${resource.error.toString()}")
        lifecycleScope.launch {
        viewModel.saveAuthToken(resource.data?.key!!)

        val tokenEmpty = sessionManager.fetchAuthToken()?.isNotEmpty() ?: true
        val tokenNull = sessionManager.fetchAuthToken() == null

        if (!tokenEmpty && !tokenNull) {
        resource.data?.let { updateFCMToken(it.user) }
        }
        }

        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
        resource is (Resource.Failure) && (resource.data == null) -> {
        Log.d(TAG, "onViewCreated: ${resource.error.toString()}")
        when (resource.error) {
        is HttpException -> when {
        resource.error.code() == 400 -> {
        Toast.makeText(
        requireContext(),
        "Bad Request",
        Toast.LENGTH_SHORT
        ).show()
        }
        resource.error.code() == 401 -> {
        Toast.makeText(
        requireContext(),
        "You are not authorized",
        Toast.LENGTH_SHORT
        ).show()
        }
        else -> {
        Toast.makeText(
        requireContext(),
        "Unknown Http Error Occurred",
        Toast.LENGTH_SHORT
        ).show()
        }

        }
        is SocketException -> {
        Toast.makeText(requireContext(), "SocketException", Toast.LENGTH_SHORT)
        .show()
        }
        else -> {
        Toast.makeText(
        requireContext(),
        "Unknown Error Occurred",
        Toast.LENGTH_SHORT
        ).show()
        }

        }
        Log.d("Failure", "Resource Failure")
        //                    handleApiError(resource) { login() }
        }
        resource is (Resource.Loading) && (resource.data == null) -> {
        binding.progressbar.visible(true)
        }
        else -> {
        // do something here
        Toast.makeText(
        requireContext(),
        "Resource Data: ${resource.data} and Error: ${resource.error}",
        Toast.LENGTH_SHORT
        ).show()
        }
        }
        }
         */


        // controls any kind of live update made in the case of logging in
        viewModel.loginResponse.observe(viewLifecycleOwner) { resource ->
            //            Snackbar.make(requireView(), "Login response", Snackbar.LENGTH_LONG).show()
            binding.progressbar.visible(resource is Resource.Loading)
            when (resource) {
                is Resource.Success -> {
                    viewLifecycleOwner.lifecycleScope.launch {
                        // save both the authToken and the LoggedIn userId
                        viewModel.saveAuthToken(resource.data?.key!!)
                        viewModel.saveUserId(resource.data.user.pk)
//
//                        val tokenEmpty = sessionManager.fetchAuthToken()?.isNotEmpty() ?: true
//                        val tokenNull = sessionManager.fetchAuthToken() == null
//
//                        if (!tokenEmpty && !tokenNull) {
//                            resource.data?.let { updateFCMToken(it.user) }
//                        }
                    }

                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
                is Resource.Failure -> {
                    Log.d("Failure", "Resource Failure")
                    handleApiError(resource) { login() }
                }
                else -> {
                    // do something here
                    Toast.makeText(
                        requireContext(),
                        "Resource Data: ${resource.data} and Error: ${resource.error}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // handles what things to do when clicking the login button
        binding.buttonLogin.setOnClickListener {
            if (Connectivity.isConnectedOrConnecting(requireContext())) {
                login()
            } else {
//                view.showWarningSnackBar("Check Your Internet Connection.")
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }

    }

    private fun navToForgetPasswordFragment(view: View) {
        view.findNavController().navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
    }

    private fun login() {
        val email = binding.editTextTextEmailAddress.text.toString().trim()
        val password = binding.editTextTextPassword.text.toString().trim()
        // validate the form
        viewModel.login(email, password)
    }

}


