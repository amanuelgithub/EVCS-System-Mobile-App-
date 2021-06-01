package com.amanuel.evscsystem.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.data.repository.UserRepository
import com.amanuel.evscsystem.data.responses.LoginResponse
import com.amanuel.evscsystem.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(
        private val repository: UserRepository
) : BaseViewModel(repository) {

    private val _user: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val user: LiveData<Resource<LoginResponse>>
        get() = _user


    fun getUser() = viewModelScope.launch {
        _user.value = Resource.Loading
        _user.value = repository.getUser()
    }

}