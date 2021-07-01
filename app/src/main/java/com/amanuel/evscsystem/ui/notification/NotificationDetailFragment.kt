package com.amanuel.evscsystem.ui.notification

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.data.models.Notification
import com.amanuel.evscsystem.databinding.FragmentNotificationsDetailBinding


class NotificationDetailFragment : Fragment(R.layout.fragment_notifications_detail) {

    private lateinit var binding: FragmentNotificationsDetailBinding
    private lateinit var notification: Notification

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNotificationsDetailBinding.bind(view)

        val args = NotificationDetailFragmentArgs.fromBundle(requireArguments())
        // notification sent form the NotificationsFragment
        notification = args.notification

        Toast.makeText(requireActivity(), "Notification: ${notification.id}", Toast.LENGTH_SHORT)
            .show()
    }


}