package com.amanuel.evscsystem.ui.settings

import android.os.Bundle
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.amanuel.evscsystem.R

class SettingsPreferenceFragment : PreferenceFragmentCompat(),
    Preference.OnPreferenceClickListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_preferences, rootKey)

        // Set Preference Change Listeners
        val logoutPreference: Preference? = findPreference("user_logout")
        logoutPreference?.setOnPreferenceClickListener { onPreferenceClick(it) }
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        if (preference?.key.equals("user_logout")) {
            // todo: logout the user from the application
            Toast.makeText(
                requireContext(), "user logout preference is clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }
        return true
    }
}