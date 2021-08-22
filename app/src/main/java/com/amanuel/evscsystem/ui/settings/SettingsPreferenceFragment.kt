package com.amanuel.evscsystem.ui.settings

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.amanuel.evscsystem.ContextUtils
import com.amanuel.evscsystem.LocaleHelper
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.data.SessionManager
import com.amanuel.evscsystem.ui.home.HomeFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class SettingsPreferenceFragment : PreferenceFragmentCompat(),
    Preference.OnPreferenceClickListener {

    @Inject
    lateinit var sessionManager: SessionManager

    private val viewModel: SettingsViewModel by viewModels()

    companion object{
        private const val TAG = "SettingsPreferenceFragm"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_preferences, rootKey)


        val accountPreference: Preference? = findPreference("fcm_token")
        accountPreference?.setOnPreferenceClickListener { onPreferenceClick(it) }

        val fcmRegPreference: Preference? = findPreference("fcm_token")
        fcmRegPreference?.setOnPreferenceClickListener { onPreferenceClick(it) }

        // Set Preference Change Listeners
        // handle the change in the preference of the logout preference
        val logoutPreference: Preference? = findPreference("user_logout")
        logoutPreference?.setOnPreferenceClickListener { onPreferenceClick(it) }

        // handle  language preference
        val languagePreference: Preference? = findPreference("language")
        languagePreference?.setOnPreferenceClickListener { onPreferenceClick(it) }

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

    override fun onPreferenceClick(preference: Preference?): Boolean {

        // Register FCM Token
        if (preference?.key.equals("fcm_token")) {
            // This is to test and log the fcm token when the application is installed
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new FCM registration token
                val fcm_token = task.result

                // Log and toast
                val msg = getString(R.string.msg_token_fmt, fcm_token)
                Log.d(TAG, msg)
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            })


            // from the preferences find 1. userId 2. authToken
            // check to see whether they are null or not
            viewLifecycleOwner.lifecycleScope.launch {
                val userId = sessionManager.fetchUserId()
                val authToken = sessionManager.fetchAuthToken()

                if (!authToken.isNullOrEmpty()){
                    onUpdateFCMTokenInServer(userId)
                    Toast.makeText(
                        requireContext(),
                        "User: $userId and Authtoken: $authToken",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Logout User
        if (preference?.key.equals("user_logout")) {
            // todo: logout the user from the application
            Toast.makeText(
                requireContext(), "user logout preference is clicked!",
                Toast.LENGTH_SHORT
            ).show()

            // remove both auth token and user id when signing out
            viewModel.deleteAuthToken()
            viewModel.deleteUserId()
            // logging the user out of the application
            viewModel.logout()

            Toast.makeText(
                requireContext(),
                "user token: ${sessionManager.fetchAuthToken()}} user id: ${sessionManager.fetchUserId()}",
                Toast.LENGTH_SHORT
            ).show()
            // finally navigate back to LoginFragment
            findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
        }

        // change language
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

        if (preference?.key.equals("account_settings")){
            findNavController().navigate(R.id.action_settingsFragment_to_userProfileFragment2)
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