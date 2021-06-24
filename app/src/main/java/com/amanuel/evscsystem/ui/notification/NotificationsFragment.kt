package com.amanuel.evscsystem.ui.notification

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.data.models.Notification
import com.amanuel.evscsystem.databinding.FragmentNotificationsBinding
import com.amanuel.evscsystem.databinding.NotificationBottomSheetDialogBinding
import dagger.hilt.android.AndroidEntryPoint

//class NotificationsFragment : Fragment(R.layout.fragment_notifications) {
@AndroidEntryPoint
class NotificationsFragment : Fragment(R.layout.fragment_notifications),
    NotificationAdapter.onLocationImageViewClickListener {

    private val viewModel: NotificationsViewModel by viewModels()

    private lateinit var binding: FragmentNotificationsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNotificationsBinding.bind(view)

        val notificationsAdapter = NotificationAdapter(this)
        binding.apply {
            recyclerviewNotifications.apply {
                adapter = notificationsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        viewModel.notifications.observe(viewLifecycleOwner) {
            notificationsAdapter.submitList(it)
        }

//        createNotificationsRecyclerView()
    }


    // by implementing the NotificationAdapter.onLocationImageViewClickListener
    // i am able to receive the notification data sent from the adapter.
    // todo: but this needs to be replaced with the detailNotificationFragment
    override fun onLocationImageViewClick(notificationId: Int, notificationPlateNumber: Int) {
        val bundle = Bundle()
        bundle.putInt("notificationId", notificationId)
        bundle.putInt("notificationPlateNumber", notificationPlateNumber)
        findNavController().navigate(R.id.action_global_notificationsDetailFragment, bundle)
        Toast.makeText(
            requireContext(),
            "Item $notificationId clicked. PN: $notificationPlateNumber",
            Toast.LENGTH_SHORT
        ).show()
    }

    // when the more options Image is clicked show a bottom sheet
    // to the users to prompt it to do things
    override fun onMoreOptionClick(position: Int) {
        val notificationBottomSheetDialog = NotificationBottomSheetDialog()
        notificationBottomSheetDialog.show(parentFragmentManager, "NotificationBottomSheetDialog")

    }

}