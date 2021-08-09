package com.amanuel.evscsystem.ui.record

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.databinding.FragmentRecordsBinding
import com.amanuel.evscsystem.ui.dialogs.FilterSortDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordsFragment : Fragment(R.layout.fragment_records) {

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