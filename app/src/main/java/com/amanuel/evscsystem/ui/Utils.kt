package com.amanuel.evscsystem.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.ui.auth.LoginFragment
import com.amanuel.evscsystem.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar


// Note: creating an extension function
fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        // this flags restrict the user from accessing the login page after successful authentication
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}


// a function to show and hide the progress bar
fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}


// extension function for creating a custom snackbar
fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}


// a function that utilize the custom snackbar to handel the api error
fun Fragment.handleApiError(
        failure: Resource.Failure,
        retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackbar("Please check your internet connection!", retry)
        failure.errorCode == 401 -> {
            if (this is LoginFragment) {
                requireView().snackbar("You've entered incorrect email or password")
            } else {
                (this as BaseFragment<*, *, *>).logout()
            }
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
        }
    }
}
