package com.amanuel.evscsystem.ui.notification

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.data.db.models.Notification
import com.amanuel.evscsystem.databinding.FragmentNotificationsDetailBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior


class NotificationDetailFragment : Fragment(R.layout.fragment_notifications_detail) {

    private lateinit var binding: FragmentNotificationsDetailBinding
    private lateinit var notification: Notification

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNotificationsDetailBinding.bind(view)

        val args = NotificationDetailFragmentArgs.fromBundle(requireArguments())
        // notification sent form the NotificationsFragment
        // a url for that specific notification is required
        notification = args.notification

        Toast.makeText(requireActivity(), "Notification: ${notification.id}", Toast.LENGTH_SHORT)
            .show()

        binding.apply {
            plateNumberTextView.text = notification.plate_number
            vehicleSpeedTextView.text = notification.vehicle_speed.toString()
            latitudeDetailTextView.text = notification.latitude.toString()
//            dateCreatedTextView.text = notification.createFormattedDate.toString()

        }


        binding.apply {
            expandableImageButton.setOnClickListener {
                if (expandableAvailActions.visibility == View.GONE) {
                    expandAvailActions()
                } else {
                    collapseAvailActions()
                }
            }

            // start the avail actions onClickListeners
            locationOnMapImgBtn.setOnClickListener {
                startMapsFragment()
            }

            writeReportImgBtn.setOnClickListener {
                startReportFragment()
            }
            // end the avail actions onClickListeners

        }

        // bottom sheet
//        initBottomSheet()
    }

    private fun startReportFragment() {
        val bundle = Bundle()
        bundle.putInt("record_id", notification.record_id)
        bundle.putString("plate_number", notification.plate_number)

        val navController = findNavController()
        navController.navigate(R.id.action_notificationsDetailFragment_to_reportFragment, bundle)
    }

    private fun startMapsFragment() {
        val bundle = Bundle()
        bundle.putParcelable("notification", notification)

        findNavController().navigate(R.id.action_notificationsDetailFragment_to_mapsFragment, bundle)
    }

    private fun FragmentNotificationsDetailBinding.collapseAvailActions() {
        TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
        expandableAvailActions.visibility = View.GONE
        expandableImageButton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
    }

    private fun FragmentNotificationsDetailBinding.expandAvailActions() {
        TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
        expandableAvailActions.visibility = View.VISIBLE
        expandableImageButton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
    }

    private fun initBottomSheet() {
        BottomSheetBehavior.from(binding.bottomSheetNotifyDetail).apply {
            peekHeight = 100
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}