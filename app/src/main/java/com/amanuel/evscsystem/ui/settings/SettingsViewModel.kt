package com.amanuel.evscsystem.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amanuel.evscsystem.data.UserPreferences
import com.amanuel.evscsystem.data.repository.AuthRepository
import com.amanuel.evscsystem.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences
) : ViewModel(){


    fun logout() = viewModelScope.launch {
        authRepository.logout()
        userPreferences.clear()
    }
}