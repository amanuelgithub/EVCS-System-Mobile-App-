package com.amanuel.evscsystem.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.databinding.FragmentSearchBinding

class SearchFragment : Fragment(R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSearchBinding.bind(view)

    }


}