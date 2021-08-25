package com.amanuel.evscsystem.ui.change_password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.databinding.FragmentChangePasswordBinding
import com.amanuel.evscsystem.utilities.EVSCDialogMsg
import com.amanuel.evscsystem.utilities.showErrorSnackBar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : Fragment(R.layout.fragment_change_password) {

    private lateinit var binding: FragmentChangePasswordBinding

    private val viewModel: ChangePasswordViewModel  by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentChangePasswordBinding.bind(view)

        binding.oldPasswordEditText.addTextChangedListener(ValidationTextWatcher(binding.oldPasswordEditText))
        binding.newPasswordEditText.addTextChangedListener(ValidationTextWatcher(binding.newPasswordEditText))
        binding.newPasswordAgainEditText.addTextChangedListener(ValidationTextWatcher(binding.newPasswordAgainEditText))

        binding.changePasswordBtn.setOnClickListener {
            if (!validatePassword(binding.oldPasswordEditText, binding.oldPasswordTextInputLayout)) {
                return@setOnClickListener;
            }
            if (!validatePassword(binding.newPasswordEditText, binding.newPasswordTextInputLayout)) {
                return@setOnClickListener;
            }
            if (!validatePassword(binding.newPasswordAgainEditText, binding.newPasswordAgainTextInputLayout)) {
                return@setOnClickListener;
            }

            val oldPassword = binding.oldPasswordEditText.text.toString()
            val newPassword = binding.newPasswordEditText.text.toString()
            val newPasswordAgain = binding.newPasswordAgainEditText.text.toString()

            if (newPassword != newPasswordAgain){
                binding.oldPasswordTextInputLayout.error = "new password does not match!"
                return@setOnClickListener
            }

            view?.let {
                viewModel.changePassword(it, oldPassword, newPassword) { any ->
                    if (any != null) {
                        Toast.makeText(requireContext(), "User updated successfully!!", Toast.LENGTH_SHORT).show()

                        EVSCDialogMsg.showSuccessAlert(
                            requireContext(),
                            "You have Successfully change password!!",
                            "You have successfully change your older password to $newPassword"
                        ) { dialog, which ->
                            findNavController().navigate(R.id.action_changePasswordFragment_to_userProfileFragment)
                        }

                    } else {
                        view.showErrorSnackBar("Profile Update Failure!")
                    }
                }
            }


        }



    }

    private fun validatePassword(textInputEditText: TextInputEditText, textInputLayout: TextInputLayout): Boolean {
        if (textInputEditText.text.toString().trim().isEmpty()) {
            textInputLayout.error = "Password is required"
//            requestFocus(password)
            return false
        } else if (textInputEditText.getText().toString().length < 6) {
            textInputLayout.error = "Password can't be less than 6 digit"
//            requestFocus(password)
            return false
        } else {
            textInputLayout.isErrorEnabled = false
        }
        return true
    }

    inner class ValidationTextWatcher(private val view: View) :
        TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                binding.oldPasswordEditText.id -> !validatePassword(binding.oldPasswordEditText, binding.oldPasswordTextInputLayout)
                binding.newPasswordEditText.id -> !validatePassword(binding.newPasswordEditText, binding.newPasswordTextInputLayout)
                binding.newPasswordAgainEditText.id -> !validatePassword(binding.newPasswordAgainEditText, binding.newPasswordAgainTextInputLayout)
            }
        }
    }

}