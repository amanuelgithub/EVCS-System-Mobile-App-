package com.amanuel.evscsystem.ui.settings

//import com.amanuel.evscsystem.databinding.FragmentSettingsBinding
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {
//    private val viewModel: SettingsViewModel by viewModels()

    private lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)

        if (binding.settingsPreferenceContainer != null) {
            if (savedInstanceState != null) return

            childFragmentManager.beginTransaction()
                .add(R.id.settings_preference_container, SettingsPreferenceFragment()).commit()
        }

//        binding.logoutBtn.setOnClickListener {
//            // logout user
//            viewModel.logout()
//            val navController = findNavController()
//            navController.navigate(R.id.action_global_loginFragment)
//
//        }

//        setHasOptionsMenu(false) // explicitly stating that it has no options menu
    }


}