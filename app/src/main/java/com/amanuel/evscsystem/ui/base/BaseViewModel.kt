package com.amanuel.evscsystem.ui.base

import androidx.lifecycle.ViewModel
import com.amanuel.evscsystem.data.network.UserApi
import com.amanuel.evscsystem.data.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseViewModel(private val repository: BaseRepository) : ViewModel()