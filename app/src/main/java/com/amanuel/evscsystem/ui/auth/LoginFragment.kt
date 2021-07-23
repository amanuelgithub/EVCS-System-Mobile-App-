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
import com.amanuel.evscsystem.data.UserPreferences
import com.amanuel.evscsystem.data.models.User
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.databinding.FragmentLoginBinding
import com.amanuel.evscsystem.ui.enable
import com.amanuel.evscsystem.ui.handleApiError
import com.amanuel.evscsystem.ui.visible
import com.amanuel.evscsystem.utilities.ViewUtils.Companion.showWarningSnackBar
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    companion object {
        private const val TAG = "LoginFragment"
    }

    private val viewModel: AuthViewModel by viewModels()

    private lateinit var preferences: UserPreferences

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)

        preferences = UserPreferences(requireContext())

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
        viewModel.loginResponse.observe(viewLifecycleOwner) { resource ->
            Snackbar.make(requireView(), "Login response", Snackbar.LENGTH_LONG).show()
            binding.progressbar.visible(resource is Resource.Loading)
            when (resource) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAuthToken(resource.data?.key!!)
                        Toast.makeText(requireContext(), resource.data?.key!!, Toast.LENGTH_SHORT)
                            .show()
//                        saveFCMToken to server
                        if (preferences.fcmToken.toString().isNotEmpty()) {
                            updateFCMToken(resource.data.user)
                        }

                        //@todo 2: check the traffic police selected a location: and based on that move either to fragment_home.xml or to fragment_location_config.xml
                        //@todo 3: also replace the below code with one that will work with fragment
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
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
                view.showWarningSnackBar("Check Your Internet Connection.")
            }
//            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
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

    /**
     * Method that will update the server of the current fcm_token.
     * It as well save the fcm_token to Preferences that will later be used
     * to check if need to send it to the server.
     */
    private fun updateFCMToken(user: User) {
        // find user and fcm_token
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val fcm_token = task.result
            // update the traffic police table
            if (fcm_token != null) {
                lifecycleScope.launch {
                    // save to the preferences
                    viewModel.saveFCMTokenToPreferences(fcm_token)
                }
                // update the fcm_token
                viewModel.updateFCMToken(user.pk, fcm_token)
            }
            // Log and toast
            val msg = getString(R.string.msg_token_fmt, fcm_token)
            Log.d(TAG, msg)
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        })
    }

}


