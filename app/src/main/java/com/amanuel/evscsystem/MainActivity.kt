package com.amanuel.evscsystem

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.amanuel.evscsystem.data.UserPreferences
import com.amanuel.evscsystem.ui.auth.AuthActivity
import com.amanuel.evscsystem.ui.home.HomeActivity
import com.amanuel.evscsystem.ui.startNewActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userPreferences = UserPreferences(this)

        userPreferences.authToken.asLiveData().observe(this, {
            val activity = if (it == null) AuthActivity::class.java else HomeActivity::class.java
            startNewActivity(activity)
        })
    }
}