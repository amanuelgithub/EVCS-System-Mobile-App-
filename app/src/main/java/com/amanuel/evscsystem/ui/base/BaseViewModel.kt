package com.amanuel.evscsystem.ui.base

import androidx.lifecycle.ViewModel
import com.amanuel.evscsystem.data.network.UserApi
import com.amanuel.evscsystem.data.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext



abstract class BaseViewModel(
    private val repository: BaseRepository
) : ViewModel() {

    suspend fun logout(api: UserApi) = withContext(Dispatchers.IO) { repository.logout(api) }

    // since sending the fcm token can be done any where
}