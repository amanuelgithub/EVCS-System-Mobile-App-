package com.amanuel.evscsystem.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class Connectivity {
    companion object {
        fun isConnectedOrConnecting(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true
        }
    }
}