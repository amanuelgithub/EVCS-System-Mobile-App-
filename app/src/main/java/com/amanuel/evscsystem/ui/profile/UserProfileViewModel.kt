package com.amanuel.evscsystem.ui.profile

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amanuel.evscsystem.data.db.models.Report
import com.amanuel.evscsystem.data.db.models.User
import com.amanuel.evscsystem.data.network.UserApi
import com.amanuel.evscsystem.data.repository.UserRepository
import com.amanuel.evscsystem.utilities.showErrorSnackBar
import com.amanuel.evscsystem.utilities.showWarningSnackBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userApi: UserApi,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User>
        get() = _user

    suspend fun getUserFromRoom(userId: Int) = viewModelScope.launch {
        _user.value = userRepository.getUserFromRoom(userId)
    }

    suspend fun insertUser(user: User) = viewModelScope.launch {
        userRepository.insertUser(user)
    }

    suspend fun deleteUser() = viewModelScope.launch {
        userRepository.deleteUser()
    }


    fun updateUserProfile(
        view: View,
        userId: Int,
        id: Int,
        username: String,
        email: String,
        firstName: String,
        lastName: String,
        onResult: (User?) -> Unit
    ) {
        userApi.updateProfile(userId, id, username, email, firstName, lastName).enqueue(
            object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {

                    if (t is IOException) {
                        view.showErrorSnackBar("This is an actual network failure: ${t.localizedMessage}")
                    }
                    if (t is HttpException) {
                        when {
                            t.code() == 401 -> {
                                view.showWarningSnackBar("Unauthorized Access!!")
                            }
                            else -> {
                                view.showErrorSnackBar("This is an actual network failure: ${t.localizedMessage}")
                            }
                        }
                    } else {
                        view.showErrorSnackBar("conversion issue! big problems :( ${t.localizedMessage}")
                    }
                    onResult(null)
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val reportedReport = response.body()
                    onResult(reportedReport)
                }
            }
        )
    }

}