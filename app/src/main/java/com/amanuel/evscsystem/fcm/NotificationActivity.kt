package com.amanuel.evscsystem.fcm

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.databinding.ActivityNotificationBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logTokenButton.setOnClickListener {
            // Get token
            // [START log_reg_token]
            Firebase.messaging.token.addOnCompleteListener ( OnCompleteListener {task ->
                if (!task.isSuccessful){
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    Toast.makeText(this, "fetching registration token failed ", Toast.LENGTH_SHORT)
                        .show()
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                // Log and toast
                val msg = getString(R.string.msg_token_fmt, token)
                Log.d(TAG, msg)

                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()

            } )
            // [END log_reg_token]
        }

    }


    companion object{
        private val TAG = "NotificationActivity"
    }


}