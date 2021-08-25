package com.amanuel.evscsystem.ui.record

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.databinding.FragmentRecordsBinding
import com.amanuel.evscsystem.ui.dialogs.FilterSortDialogFragment
import com.amanuel.evscsystem.utilities.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class RecordsFragment : Fragment(R.layout.fragment_records),  FilterSortDialogFragment.ReceiveFilteringOptionsData{


    // starting filter sort options
    private lateinit var plateNumber: String
    private lateinit var selectedDate: String
    private var status by Delegates.notNull<Boolean>()
    // starting filter sort options


    private val viewModel: RecordsViewModel by viewModels()

    private lateinit var binding: FragmentRecordsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRecordsBinding.bind(view)

        val recordsAdapter = RecordsAdapter()
        binding.apply {
            recyclerviewRecords.apply {
                adapter = recordsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewModel.records.observe(viewLifecycleOwner) { resource ->
            recordsAdapter.submitList(resource.data)

            binding.apply {
                progressBarRecords.isVisible = resource is Resource.Loading && resource.data.isNullOrEmpty()
                errorTextViewRecords.isVisible = resource is Resource.Failure && resource.data.isNullOrEmpty()
                errorTextViewRecords.text = resource.error?.localizedMessage
            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_all_search_records_fragment, menu)

        // convert the search menu item to a search view
        val search = menu?.findItem(R.id.search_data_menu_option)
        val searchView = search?.actionView as? SearchView
//        searchView?.isSubmitButtonEnabled = true
        searchView?.queryHint = "Search Records..."

        searchView?.onQueryTextChanged {
            // update search query
            viewModel.searchQuery.value = it
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter_menu_option -> {
                // show filter sort fragment
                FilterSortDialogFragment(this).show(childFragmentManager, FilterSortDialogFragment.TAG)
            }
            R.id.action_sort_by_plate_number -> {
                viewModel.sortOrder.value = SortOrder.BY_PLATE_NUMBER
                return true
            }
            R.id.action_sort_by_date -> {
                viewModel.sortOrder.value = SortOrder.BY_DATE
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setFilterOptionValues(plateNumber: String, selectedDate: String, status: Boolean) {
        this.plateNumber = plateNumber
        this.selectedDate = selectedDate
        this.status = status

        Toast.makeText(
            requireContext(),
            "${this.plateNumber} ${this.selectedDate} ${this.status}",
            Toast.LENGTH_SHORT
        ).show()


    }

}