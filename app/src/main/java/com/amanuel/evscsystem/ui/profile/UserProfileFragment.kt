package com.amanuel.evscsystem.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.data.SessionManager
import com.amanuel.evscsystem.databinding.FragmentUserProfileBinding
import com.amanuel.evscsystem.ui.enable
import com.amanuel.evscsystem.utilities.EVSCDialogMsg
import com.amanuel.evscsystem.utilities.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    //    private var userId by Delegates.notNull<Int>()
//
    private lateinit var binding: FragmentUserProfileBinding

    private val viewModel: UserProfileViewModel by viewModels()

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentUserProfileBinding.bind(view)

        val userId = sessionManager.fetchUserId()

        // this may cause a crash for the first time(Since userId may not be saved in the preference)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getUserFromRoom(userId)
        }

        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null){
                Toast.makeText(requireContext(), "User Data: ${user.email}", Toast.LENGTH_SHORT).show()

                binding.apply {
                    profileFirstNameEditText.setText(user.first_name)
                    profileLastNameEditText.setText(user.last_name)
                    profileUsernameEditText.setText(user.username)
                    profileEmailEditText.setText(user.email)

                    fullNameTextView.text = "${profileFirstNameEditText.text.toString()} ${profileLastNameEditText.text.toString()}"
//                    profilePasswordEditText.setText(user.password)
//                    profilePhoneNumberEditText.setText(user.phone)
                }
            }
        }

//        val args = UserProfileFragmentArgs.fromBundle(requireArguments())
//        userId = args.userId

        // handles the clicking event of the floating action button
        binding.profileFloatingActionButton.setOnClickListener {
            showEditableProfileLayout()
            binding.changePasswordMoveBtn.visibility = View.GONE
        }


        binding.profileUsernameEditText.addTextChangedListener(ValidationTextWatcher(binding.profileUsernameEditText));
        binding.profileEmailEditText.addTextChangedListener(ValidationTextWatcher(binding.profileEmailEditText));
        binding.profileFirstNameEditText.addTextChangedListener(ValidationTextWatcher(binding.profileFirstNameEditText));
        binding.profileLastNameEditText.addTextChangedListener(ValidationTextWatcher(binding.profileLastNameEditText));

        binding.saveProfileButton.setOnClickListener {
            // find all value from the
            if (!validateUsername()) {
                return@setOnClickListener;
            }
            if (!validateEmail()) {
                return@setOnClickListener;
            }
            if (!validateFirstName()) {
                return@setOnClickListener;
            }
            if (!validateLastName()) {
                return@setOnClickListener;
            }

            val firstName = binding.profileFirstNameEditText.text.toString()
            val lastName = binding.profileLastNameEditText.text.toString()
            val username = binding.profileUsernameEditText.text.toString()
            val email = binding.profileEmailEditText.text.toString()

//            Toast.makeText(requireContext(), "$firstName $lastName $username $email", Toast.LENGTH_SHORT).show()

            view?.let {
                viewModel.updateUserProfile(it, userId, userId, username, email, firstName, lastName) { user ->
                    if (user != null) {

                        viewLifecycleOwner.lifecycleScope.launch {
                            runBlocking {
                                viewModel.deleteUser()
                                viewModel.insertUser(user)
                            }
                        }


                        Toast.makeText(requireContext(), "User updated successfully!!", Toast.LENGTH_SHORT).show()

                        EVSCDialogMsg.showSuccessAlert(
                            requireContext(),
                            "User Updated Success!!!",
                            "You have successfully updated Your profile: $firstName $lastName"
                        ) { dialog, which ->
                            showMainProfileLayout()
                            binding.changePasswordMoveBtn.visibility = View.VISIBLE
//                            findNavController().navigate(R.id.action_reportFragment_to_notificationsFragment)
                        }

                    } else {
                        view.showErrorSnackBar("Profile Update Failure!")
                    }
                }
            }

        }

        binding.cancelProfileButton.setOnClickListener {
            showMainProfileLayout()
            binding.changePasswordMoveBtn.visibility = View.VISIBLE
        }


        binding.userProfileSelectionImageView.setOnClickListener {
            selectImageOrTakePicture()
        }

        binding.changePasswordMoveBtn.setOnClickListener {
            // navigate to change password page
            findNavController().navigate(R.id.action_userProfileFragment_to_changePasswordFragment)
        }
    }

    // when the imageView is clicked allow user to select image from available
    // pictures or prompt them to take a picture
    private fun selectImageOrTakePicture() {
        /** todo: allow selection of user profile image from existing pictures or take new picture using the camera */
    }

    private fun showMainProfileLayout() {
        TransitionManager.beginDelayedTransition(binding.profileHeaderLayout, AutoTransition())
        binding.profileHeaderLayout.visibility = View.VISIBLE
        binding.editableProfileHeaderLayout.visibility = View.GONE

        disableTextFields()
        showFloatingActionButton()
    }

    private fun showEditableProfileLayout() {
        // hide the main profile layout
        // show the editable profile layout
        TransitionManager.beginDelayedTransition(
            binding.editableProfileHeaderLayout,
            AutoTransition()
        )
        binding.profileHeaderLayout.visibility = View.GONE
        binding.editableProfileHeaderLayout.visibility = View.VISIBLE

        enableTextFields()
        hideFloatingActionButton()
    }

    private fun enableTextFields() {
        binding.apply {
            profileFirstNameEditText.enable(true)
            profileLastNameEditText.enable(true)
            profileUsernameEditText.enable(true)
            profileEmailEditText.enable(true)
//            profilePasswordEditText.enable(true)
//            profilePhoneNumberEditText.enable(true)
        }
    }

    private fun disableTextFields() {
        binding.apply {
            profileFirstNameEditText.enable(false)
            profileLastNameEditText.enable(false)
            profileUsernameEditText.enable(false)
            profileEmailEditText.enable(false)
//            profilePasswordEditText.enable(false)
//            profilePhoneNumberEditText.enable(false)
        }
    }

    private fun hideFloatingActionButton() {
        binding.profileFloatingActionButton.visibility = View.GONE
    }

    private fun showFloatingActionButton() {
        binding.profileFloatingActionButton.visibility = View.VISIBLE
    }


    private fun validateUsername(): Boolean {
        if (binding.profileUsernameEditText.text.toString().trim().isEmpty()) {
            binding.profileUsernameTextInputLayout.error = "Username is required"
//            requestFocus(password)
            return false
        } else if (binding.profileUsernameEditText.getText().toString().length < 6) {
            binding.profileUsernameTextInputLayout.error = "Username can't be less than 6 digit"
//            requestFocus(password)
            return false
        } else {
            binding.profileUsernameTextInputLayout.isErrorEnabled = false
        }
        return true
    }

    private fun validateFirstName(): Boolean {
        if (binding.profileFirstNameEditText.text.toString().trim().isEmpty()) {
            binding.profileFirstNameTextInputLayout.error = "Firstname is required"
//            requestFocus(password)
            return false
        } else {
            binding.profileFirstNameTextInputLayout.isErrorEnabled = false
        }
        return true
    }

    private fun validateLastName(): Boolean {
        if (binding.profileLastNameEditText.text.toString().trim().isEmpty()) {
            binding.profileLastNameTextInputLayout.error = "Username is required"
//            requestFocus(password)
            return false
        } else {
            binding.profileLastNameTextInputLayout.isErrorEnabled = false
        }
        return true
    }

    private fun validateEmail(): Boolean {
        if (binding.profileEmailEditText.text.toString().trim().isEmpty()) {
//            binding.emailTextInputLayout.isErrorEnabled = false
            binding.profileEmailTextInputLayout.error = "Email is required"
        } else {
            val emailId: String = binding.profileEmailEditText.text.toString()
            val isValid = Patterns.EMAIL_ADDRESS.matcher(emailId).matches()
            if (!isValid) {
                binding.profileEmailTextInputLayout.error =
                    "Invalid Email address, ex: abc@example.com"
//                requestFocus(editText)
                return false
            } else {
                binding.profileEmailTextInputLayout.isErrorEnabled = false
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
                binding.profileUsernameEditText.id -> validateUsername()
                binding.profileEmailEditText.id -> validateEmail()
                binding.profileFirstNameEditText.id -> validateFirstName()
                binding.profileLastNameEditText.id -> validateLastName()
            }
        }
    }

}