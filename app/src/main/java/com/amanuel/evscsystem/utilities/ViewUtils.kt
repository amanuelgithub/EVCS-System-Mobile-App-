package com.amanuel.evscsystem.utilities

import android.view.View
import androidx.core.content.ContextCompat
import com.amanuel.evscsystem.R
import com.google.android.material.snackbar.Snackbar


class ViewUtils{
    companion object{

        fun View.showErrorSnackBar(error: String){
            val snackbar = Snackbar.make(this, error, Snackbar.LENGTH_SHORT)
            val snackbarView = snackbar.view
            snackbarView.setBackgroundColor(ContextCompat.getColor(context,
                R.color.snackbar_error_color
            ))
            // finally show it
            snackbar.show()
        }

        fun View.showSuccessSnackBar(message: String){
            val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
            val snackbarView = snackbar.view
            snackbarView.setBackgroundColor(ContextCompat.getColor(context,
                R.color.snackbar_success_color
            ))
            // finally show it
            snackbar.show()
        }

        fun View.showWarningSnackBar(message: String){
            val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
            val snackbarView = snackbar.view
            snackbarView.setBackgroundColor(ContextCompat.getColor(context,
                R.color.snackbar_warning_color
            ))
            // finally show it
            snackbar.show()
        }

    }
}