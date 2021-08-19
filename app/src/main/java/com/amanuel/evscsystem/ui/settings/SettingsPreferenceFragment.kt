package com.amanuel.evscsystem.ui.settings

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.amanuel.evscsystem.ContextUtils
import com.amanuel.evscsystem.LocaleHelper
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.data.SessionManager
import java.util.*


class SettingsPreferenceFragment : PreferenceFragmentCompat(),
    Preference.OnPreferenceClickListener {

    private val viewModel: SettingsViewModel by viewModels()

    companion object{
        private const val TAG = "SettingsPreferenceFragm"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_preferences, rootKey)

        // Set Preference Change Listeners
        // handle the change in the preference of the logout preference
        val logoutPreference: Preference? = findPreference("user_logout")
        logoutPreference?.setOnPreferenceClickListener { onPreferenceClick(it) }

        // handle  language preference
        val languagePreference: Preference? = findPreference("language")
        languagePreference?.setOnPreferenceClickListener { onPreferenceClick(it) }

    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        if (preference?.key.equals("user_logout")) {
            // todo: logout the user from the application
            Toast.makeText(
                requireContext(), "user logout preference is clicked!",
                Toast.LENGTH_SHORT
            ).show()

            // logging the user out of the application
            viewModel.logout()
        }
        if (preference?.key.equals("language")){
            // then it is the list preference that prompted the user select a language
            val pref = preference as ListPreference
            pref.title = resources.getString(R.string.language)
            val selectedLanguage = pref.value.toString()    // finds the selected language


//            Toast.makeText(requireContext(), "Selected Language: $selectedLanguage", Toast.LENGTH_SHORT).show()
            // change the local to the selected language
//            setLocal(selectedLanguage)



            var actualLang = "en"
            if(selectedLanguage == "English"){
                actualLang = "en"
            }else if(selectedLanguage == "Amharic"){
                actualLang = "am-rET"
            }

            Toast.makeText(requireContext(), "Selected Language: $actualLang", Toast.LENGTH_SHORT).show()
            LocaleHelper.setLocale(requireContext(), actualLang)
//
//
//            SessionManager(requireContext()).updateLanguage(actualLang)

        }

        return true
    }

    // a method to change the language of the applciation
    private fun setLocal(language: String) {
        var actualLang = "en"
        if(language == "English"){
            actualLang = "en"
        }else if(language == "Amharic"){
            actualLang = "am-rET"
        }

        val locale = Locale(actualLang)
        Locale.setDefault(locale)
        val resources: Resources = requireContext().resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

//        val sharedPrefEditor = context?.getSharedPreferences("LanSetting", id)?.edit()
//        sharedPrefEditor?.putString("My_Lang", actualLang)
//        sharedPrefEditor?.apply()

    }


    fun loadLocal(){
        val sharedPref = context?.getSharedPreferences("LanSetting",id)
        val lang = sharedPref?.getString("My_Lang", "").toString()
        setLocal(lang)
    }
}