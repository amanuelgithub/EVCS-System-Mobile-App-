package com.amanuel.evscsystem.ui.home

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.data.SessionManager
import com.amanuel.evscsystem.databinding.FragmentHomeBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    @Inject
    lateinit var sessionManager: SessionManager

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        sessionManager = SessionManager(requireContext())
        // from the preferences find 1. userId 2. authToken
        // check to see whether they are null or not
//        val userId = sessionManager.fetchUserId()
//        val authToken = sessionManager.fetchAuthToken()
//
//        if (!authToken.isNullOrEmpty()){
//            onUpdateFCMTokenInServer(userId)
//            Toast.makeText(
//                requireContext(),
//                "User: $userId and Authtoken: $authToken",
//                Toast.LENGTH_SHORT
//            ).show()
//        }

//        authToken?.let { authToken ->
//            viewModel.loginUser(userId, authToken).observe(viewLifecycleOwner){ resources ->
//
//            }
//
//        }


    }

    /**
     * Method that will update the server of the current fcm_token.
     * It as well save the fcm_token to Preferences that will later be used
     * to check if need to send it to the server.
     */
    private fun onUpdateFCMTokenInServer(userId: Int) {
        // find userId and fcm_token
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
//                    viewModel.saveFCMTokenToPreferences(fcm_token)
                }
                // update the fcm_token
                viewModel.updateFCMToken(userId, fcm_token)
            }
            // Log and toast
            val msg = getString(R.string.msg_token_fmt, fcm_token)
            Log.d(TAG, msg)
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.user_profile_menu_option) {
            // navigate to the user profile fragment(page)
            findNavController().navigate(R.id.action_homeFragment_to_userProfileFragment)
        } else {
            return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
    }


    companion object {
        private const val TAG = "HomeFragment"
    }

}