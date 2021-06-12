package com.amanuel.evscsystem.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.databinding.FragmentForgetPasswordBinding


class ForgetPasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentForgetPasswordBinding.inflate(layoutInflater)

        binding.sendCodeButton.setOnClickListener {
            view?.let { it1 -> sendVerifyCodeToEmail(it1) }
        }

        return binding.root
    }

    // moves to the email verify page
    private fun sendVerifyCodeToEmail(view: View){
        view.findNavController().navigate(R.id.action_forgetPasswordFragment_to_emailVerifyFragment)
    }


}