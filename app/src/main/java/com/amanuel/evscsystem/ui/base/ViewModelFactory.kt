package com.amanuel.evscsystem.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amanuel.evscsystem.data.repository.AuthRepository
import com.amanuel.evscsystem.data.repository.BaseRepository
import com.amanuel.evscsystem.data.repository.UserRepository
import com.amanuel.evscsystem.ui.auth.AuthViewModel
import com.amanuel.evscsystem.ui.home.HomeViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
        private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository as UserRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }

}