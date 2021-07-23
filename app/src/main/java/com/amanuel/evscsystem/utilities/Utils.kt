
package com.amanuel.evscsystem.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.ui.auth.LoginFragment
import com.google.android.material.snackbar.Snackbar
import retrofit2.HttpException


// Note: creating an extension function
fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        // this flags restrict the user from accessing the login page after successful authentication
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

/**
 * Extension function to navigate to a
 */
fun Fragment.navigate(fragmentManager: FragmentManager): Int? {
    return fragmentManager.run {
        beginTransaction()
            .replace(
                R.id.nav_host_fragment_content_main,
                this@navigate,
                this@navigate::class.simpleName
            )
            .commit()
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
fun <T> Fragment.handleApiError(
    failure: Resource<T>,
    retry: (() -> Unit)? = null
) {
    when (failure.error) {
        is HttpException -> {
            when {
                failure.error.code() == 401 -> {
                    if (this is LoginFragment) {
                        requireView().snackbar("You've entered incorrect email or password")
                    } else {
                        // todo: logout the user
//                    (this as BaseFragment).logout()
                    }
                }
                else -> {
                    val error = failure.error.response()?.errorBody().toString()
                    requireView().snackbar("Error: $error")
                }
            }
        }
        else -> {
            requireView().snackbar("Please check your internet connection!!!", retry)
        }
    }
}

// previous way to handle the the different resource errors
//fun Fragment.handleApiError(
//    failure: Resource.Failure,
//    retry: (() -> Unit)? = null
//) {
//    when {
//        failure.isNetworkError ->
//            requireView().snackbar("Please check your internet connection!", retry)
//        failure.errorCode == 401 -> {
//            if (this is LoginFragment) {
//                requireView().snackbar("You've entered incorrect email or password")
//            } else {
////                (this as BaseFragment<*, *, *>).logout()
//            }
//        }
//        else -> {
//            val error = failure.errorBody?.string().toString()
//            requireView().snackbar(error)
//        }
//    }
//}

