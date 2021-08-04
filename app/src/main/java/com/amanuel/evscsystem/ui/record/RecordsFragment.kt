package com.amanuel.evscsystem.ui.record

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.databinding.FragmentRecordsBinding
import com.amanuel.evscsystem.ui.dialogs.FilterSortDialogFragment

class RecordsFragment : Fragment(R.layout.fragment_records) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRecordsBinding.inflate(layoutInflater, container, false)

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_all_search_fragment, menu)

        // convert the search menu item to a search view
        val search = menu?.findItem(R.id.search_data_menu_option)
        val searchView = search?.actionView as? SearchView
//        searchView?.isSubmitButtonEnabled = true
        searchView?.queryHint = "Search Records..."

        //searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter_menu_option -> {
                // show filter sort fragment
                FilterSortDialogFragment().show(childFragmentManager, FilterSortDialogFragment.TAG)
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}