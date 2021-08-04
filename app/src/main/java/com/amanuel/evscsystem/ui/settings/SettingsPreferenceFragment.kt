package com.amanuel.evscsystem.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.amanuel.evscsystem.R

class SettingsPreferenceFragment : PreferenceFragmentCompat(){
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_preferences, rootKey)
    }
}