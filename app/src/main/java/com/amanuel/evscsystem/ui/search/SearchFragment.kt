package com.amanuel.evscsystem.ui.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.databinding.FragmentDialogFilterSortBinding
import com.amanuel.evscsystem.databinding.FragmentSearchBinding
import com.amanuel.evscsystem.filter.FilterSortDialogFragment

class SearchFragment : Fragment(R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSearchBinding.bind(view)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // search option should not be included in here.
//            R.id.search_menu_option -> {
//
//                true
//            }
            R.id.filter_menu_option -> {
                // todo: show the filter page
                FilterSortDialogFragment().show(
                    childFragmentManager, FilterSortDialogFragment.TAG
                )

                true
            }
            R.id.action_sort_by_date -> {
                // todo: handle the sort by date action
                true
            }
            R.id.action_sort_by_name -> {
                // todo: handle the sort by name
                true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }

        }
    }

}