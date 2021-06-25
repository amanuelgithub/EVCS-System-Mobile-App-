package com.amanuel.evscsystem.ui.notification

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.databinding.FragmentNotificationsDetailBinding


class NotificationDetailFragment : Fragment(R.layout.fragment_notifications_detail) {


    private lateinit var binding: FragmentNotificationsDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNotificationsDetailBinding.bind(view)

        // find the arguments passed from the NotificationsFragment and
        // showing them on a toast
        val args = NotificationDetailFragmentArgs.fromBundle(requireArguments())
        val notificationId = args.notificationId
        val notificationPlateNumber = args.notificationPlateNumber

        Toast.makeText(
            requireContext(),
            "Id: $notificationId and PN: $notificationPlateNumber",
            Toast.LENGTH_SHORT
        ).show()
    }

}