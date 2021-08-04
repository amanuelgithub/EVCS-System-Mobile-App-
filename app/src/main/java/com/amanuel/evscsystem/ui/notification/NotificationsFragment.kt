package com.amanuel.evscsystem.ui.notification

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.data.db.models.Notification
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.databinding.FragmentNotificationsBinding
import com.amanuel.evscsystem.ui.dialogs.FilterSortDialogFragment
import dagger.hilt.android.AndroidEntryPoint

//class NotificationsFragment : Fragment(R.layout.fragment_notifications) {
@AndroidEntryPoint
class NotificationsFragment : Fragment(R.layout.fragment_notifications),
    NotificationAdapter.NotificationWidgetsClickListenerInterface {

    private val viewModel: NotificationsViewModel by viewModels()

    private lateinit var binding: FragmentNotificationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        viewModel.notifications.observe(viewLifecycleOwner) { resource ->
            notificationsAdapter.submitList(resource.data)

            binding.apply {
                progressBar.isVisible = resource is Resource.Loading && resource.data.isNullOrEmpty()
                errorTextView.isVisible = resource is Resource.Failure && resource.data.isNullOrEmpty()
                errorTextView.text = resource.error?.localizedMessage
            }
        }

//        createNotificationsRecyclerView()
    }


    // by implementing the NotificationAdapter.onLocationImageViewClickListener
    // i am able to receive the notification data sent from the adapter.
    // todo: but this needs to be replaced with the detailNotificationFragment
    override fun onLocationImageViewClicked(notification: Notification) {
        val bundle = Bundle()
        bundle.putParcelable("notification",notification)

        findNavController().navigate(R.id.action_notificationsFragment_to_notificationsDetailFragment, bundle)
    }

    // when the more options Image is clicked show a bottom sheet
    // to the users to prompt it to do things
    override fun onMoreOptionClicked(position: Int) {
        val notificationBottomSheetDialog = NotificationBottomSheetDialog()
        notificationBottomSheetDialog.show(parentFragmentManager, "NotificationBottomSheetDialog")
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_all_search_fragment, menu)

        // converting the menu search item to a search view
        val search = menu?.findItem(R.id.search_data_menu_option)
        val searchView = search?.actionView as? SearchView
//        searchView?.isSubmitButtonEnabled = true
        searchView?.queryHint = "Search Notifications..."

//        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.filter_menu_option -> {
                // show filter sort fragment
                FilterSortDialogFragment().show(childFragmentManager, FilterSortDialogFragment.TAG)
            }
            else-> {
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}