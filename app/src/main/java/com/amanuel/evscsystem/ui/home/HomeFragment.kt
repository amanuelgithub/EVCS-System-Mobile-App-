package com.amanuel.evscsystem.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.data.SessionManager
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
//    SharedPreferences.OnSharedPreferenceChangeListener {

//    private var userId by Delegates.notNull<Int>()

    @Inject
    lateinit var sessionManager: SessionManager

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    companion object {
        private const val TAG = "HomeFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        viewModel.notifications.observe(viewLifecycleOwner) { resource ->
            val totalNotifications = resource.data?.size
            if (resource is Resource.Failure && resource.data.isNullOrEmpty()) {
                binding.allTimeNotificationsTextView.text = "0"
            } else {
                binding.allTimeNotificationsTextView.text = totalNotifications.toString()
            }
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.user_profile_menu_option) {
            // navigate to the user profile fragment(page)
//            val bundle = Bundle()
//            bundle.putInt("userId", userId)

            findNavController().navigate(R.id.action_homeFragment_to_userProfileFragment)
        } else {
            return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
    }


    // Note: for now i don't know how i will relate it to the SessionsManager class
    // but the values that can be accessed here are those that are saved from the
    // defaultSharedPreferenceObject and not from the custom SharedPreference i created in the SessionsManager
    // class.
    // So, when the value of authToken is changes(meaning it is saved) update it in the server.
//    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
//        when (key) {
//            USER_TOKEN -> {
//                val userId = sessionManager.fetchUserId()
//                if (userId != 0){
//                    onUpdateFCMTokenInServer(userId)
//                }
//            }
//        }
//    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

}