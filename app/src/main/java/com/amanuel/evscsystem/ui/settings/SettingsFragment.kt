package com.amanuel.evscsystem.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.data.UserPreferences
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.databinding.FragmentSettingsBinding
import com.amanuel.evscsystem.utilities.ViewUtils.Companion.showSuccessSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val viewModel: SettingsViewModel by viewModels()

    private lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)




        binding.logoutBtn.setOnClickListener {
            // logout user
            viewModel.logout()
            val navController = findNavController()
            navController.navigate(R.id.action_global_loginFragment)

        }

        setHasOptionsMenu(false) // explicitly stating that it has no options menu
    }


}