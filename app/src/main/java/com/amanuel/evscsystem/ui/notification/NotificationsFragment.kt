package com.amanuel.evscsystem.ui.notification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.databinding.FragmentNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint

//class NotificationsFragment : Fragment(R.layout.fragment_notifications) {
@AndroidEntryPoint
class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    private val viewModel: NotificationsViewModel by viewModels()

    private lateinit var binding: FragmentNotificationsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNotificationsBinding.bind(view)

        val notificationsAdapter = NotificationAdapter()
        binding.apply {
            recyclerviewNotifications.apply {
                adapter = notificationsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        viewModel.notifications.observe(viewLifecycleOwner){
            notificationsAdapter.submitList(it)
        }

//        createNotificationsRecyclerView()
    }

//    private fun createNotificationsRecyclerView() {
//
//    }
}