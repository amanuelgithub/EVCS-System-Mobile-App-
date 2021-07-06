package com.amanuel.evscsystem.ui.notification

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.data.models.Notification
import com.amanuel.evscsystem.databinding.FragmentNotificationsDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior


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


        binding.apply {
            expandableImageButton.setOnClickListener {
                if (expandableAvailActions.visibility == View.GONE) {
                    TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                    expandableAvailActions.visibility = View.VISIBLE
                    expandableImageButton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                } else {
                    TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                    expandableAvailActions.visibility = View.GONE
                    expandableImageButton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                }
            }
        }


        // bottom sheet
        BottomSheetBehavior.from(binding.bottomSheetNotifyDetail).apply {
            peekHeight = 100
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }


    }


    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
//        val sydney = LatLng(-34.0, 151.0)
        val astu = LatLng(8.559176747541658, 39.28568205038687)
        googleMap.addMarker(MarkerOptions().position(astu).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(astu))
    }


}