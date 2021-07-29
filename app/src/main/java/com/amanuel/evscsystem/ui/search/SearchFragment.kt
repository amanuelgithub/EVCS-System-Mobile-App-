package com.amanuel.evscsystem.ui.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.databinding.FragmentSearchBinding
import com.amanuel.evscsystem.ui.dialogs.FilterSortDialogFragment

class SearchFragment : Fragment(R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSearchBinding.bind(view)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search_menu_option -> {
                // show a dialog box to show search selection option
                showSearchOptionsDialog()
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSearchOptionsDialog() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("What do you want to search")
            .setItems(R.array.search_options_array) { dialog, which ->
                when {
                    which == 0 -> {
                        // open the notifications NotificationsSearchFragment
                        Toast.makeText(
                            requireContext(),
                            "Notifications: $which",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.action_searchFragment_to_notificationsFragment)
                    }
                    which == 1 -> {
                        // open the notifications recordsSearchFragment
                        Toast.makeText(requireContext(), "Records: $which", Toast.LENGTH_SHORT)
                            .show()
                    }
                    which == 2 -> {
                        // open the notifications VehiclesSearchFragment
                        Toast.makeText(requireContext(), "Vehicles: $which", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        builder.create().show()
    }


}