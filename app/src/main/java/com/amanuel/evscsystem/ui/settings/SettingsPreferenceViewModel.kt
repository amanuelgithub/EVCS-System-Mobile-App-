package com.amanuel.evscsystem.ui.settings

import androidx.lifecycle.ViewModel
import com.amanuel.evscsystem.data.repository.AuthRepository
import com.amanuel.evscsystem.data.repository.UserRepository
import javax.inject.Inject

class SettingsPreferenceViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
) : ViewModel(){

}