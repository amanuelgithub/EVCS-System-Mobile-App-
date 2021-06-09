package com.amanuel.evscsystem.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.databinding.FragmentForgetPasswordBinding


class ForgetPasswordFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)


        return binding.root
    }


}