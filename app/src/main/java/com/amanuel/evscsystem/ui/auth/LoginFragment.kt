package com.amanuel.evscsystem.ui.auth

import android.R.attr.password
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.connectivity.Connectivity
import com.amanuel.evscsystem.data.SessionManager
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.databinding.FragmentLoginBinding
import com.amanuel.evscsystem.ui.enable
import com.amanuel.evscsystem.ui.handleApiError
import com.amanuel.evscsystem.ui.visible
import com.amanuel.evscsystem.utilities.showErrorSnackBar
import com.amanuel.evscsystem.utilities.showWarningSnackBar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    companion object {
        private const val TAG = "LoginFragment"
    }

    private val viewModel: AuthViewModel by viewModels()

    //    private lateinit var preferences: UserPreferences
    @Inject
    lateinit var sessionManager: SessionManager

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)

//        preferences = UserPreferences(requireContext())
        sessionManager = SessionManager(requireContext())

        binding.textViewTextForgetPassword.setOnClickListener { v: View ->
            navToForgetPasswordFragment(v)
        }

        binding.progressbar.visible(false)


        binding.editTextTextPassword.addTextChangedListener(ValidationTextWatcher(binding.editTextTextPassword));
        binding.editTextTextEmailAddress.addTextChangedListener(ValidationTextWatcher(binding.editTextTextEmailAddress));

        // handles what things to do when clicking the login button
        binding.loginButton.setOnClickListener {
            if (!validatePassword()) {
                return@setOnClickListener;
            }
            if (!validateEmail()) {
                return@setOnClickListener;
            }

            if (Connectivity.isConnectedOrConnecting(requireContext())) {
                login()
            } else {
                view.showWarningSnackBar("Check Your Internet Connection.")
            }
        }

    }


    private fun navToForgetPasswordFragment(view: View) {
        view.findNavController().navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
    }

    private fun login() {
        val email = binding.editTextTextEmailAddress.text.toString().trim()
        val password = binding.editTextTextPassword.text.toString().trim()
        // validate the form
        binding.progressbar.visibility = View.VISIBLE
        viewLifecycleOwner.lifecycleScope.launch {
            delay(2000)
        }
        view?.let {
            viewModel.login(it, email, password) { loginResponse ->
                if (loginResponse != null) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        // save both the authToken and the LoggedIn userId
                        runBlocking {
                            viewModel.saveAuthToken(loginResponse.key)
                            viewModel.saveUserId(loginResponse.user.pk)

                            // save the user in the database
                            viewModel.insertUser(loginResponse.user)
                            Toast.makeText(requireContext(), "User inserted ${loginResponse.user}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    binding.progressbar.visibility = View.GONE

//                    val bundle = Bundle()
//                    bundle.putInt("userId", loginResponse.user.pk)

                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    binding.progressbar.visibility = View.GONE
                    view?.showErrorSnackBar("Authentication Failure")
                }
            }
        }
    }


    private fun validatePassword(): Boolean {
        if (binding.editTextTextPassword.text.toString().trim().isEmpty()) {
            binding.passwordTextInputLayout.error = "Password is required"
//            requestFocus(password)
            return false
        } else if (binding.editTextTextPassword.getText().toString().length < 6) {
            binding.passwordTextInputLayout.error = "Password can't be less than 6 digit"
//            requestFocus(password)
            return false
        } else {
            binding.passwordTextInputLayout.isErrorEnabled = false
        }
        return true
    }

    private fun validateEmail(): Boolean {
        if (binding.editTextTextEmailAddress.text.toString().trim().isEmpty()) {
//            binding.emailTextInputLayout.isErrorEnabled = false
            binding.emailTextInputLayout.error = "Email is required"
        } else {
            val emailId: String = binding.editTextTextEmailAddress.text.toString()
            val isValid = Patterns.EMAIL_ADDRESS.matcher(emailId).matches()
            if (!isValid) {
                binding.emailTextInputLayout.error = "Invalid Email address, ex: abc@example.com"
//                requestFocus(editText)
                return false
            } else {
                binding.emailTextInputLayout.isErrorEnabled = false
            }
        }
        return true
    }

    inner class ValidationTextWatcher(private val view: View) :
        TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                binding.editTextTextPassword.id -> validatePassword()
                binding.editTextTextEmailAddress.id -> validateEmail()
            }
        }
    }

}


