package com.amanuel.evscsystem.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.databinding.FragmentDialogFilterSortBinding


class FilterSortDialogFragment : DialogFragment(R.layout.fragment_dialog_filter_sort) {

    private lateinit var binding: FragmentDialogFilterSortBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogFilterSortBinding.inflate(layoutInflater)
        return binding.root
    }


    companion object{
        const val TAG = "FragmentDialogFilterSortBinding"
    }

}