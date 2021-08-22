package com.amanuel.evscsystem.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.databinding.FragmentUserProfileBinding
import com.amanuel.evscsystem.ui.enable

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    private lateinit var binding: FragmentUserProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentUserProfileBinding.bind(view)

        // handles the clicking event of the floating action button
        binding.profileFloatingActionButton.setOnClickListener {
            showEditableProfileLayout()
        }

        binding.saveProfileButton.setOnClickListener {
            showMainProfileLayout()
        }

        binding.cancelProfileButton.setOnClickListener {
            showMainProfileLayout()
        }


        binding.userProfileSelectionImageView.setOnClickListener {
            selectImageOrTakePicture()
        }
    }

    // when the imageView is clicked allow user to select image from available
    // pictures or prompt them to take a picture
    private fun selectImageOrTakePicture(){
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
        TransitionManager.beginDelayedTransition(binding.editableProfileHeaderLayout, AutoTransition())
        binding.profileHeaderLayout.visibility = View.GONE
        binding.editableProfileHeaderLayout.visibility = View.VISIBLE

        enableTextFields()
        hideFloatingActionButton()
    }

    private fun enableTextFields(){
        binding.apply {
            profileFirstNameEditText.enable(true)
            profileLastNameEditText.enable(true)
            profileUsernameEditText.enable(true)
            profileEmailEditText.enable(true)
            profilePasswordEditText.enable(true)
            profilePhoneNumberEditText.enable(true)
        }
    }

    private fun disableTextFields(){
        binding.apply {
            profileFirstNameEditText.enable(false)
            profileLastNameEditText.enable(false)
            profileUsernameEditText.enable(false)
            profileEmailEditText.enable(false)
            profilePasswordEditText.enable(false)
            profilePhoneNumberEditText.enable(false)
        }
    }

    private fun hideFloatingActionButton(){
        binding.profileFloatingActionButton.visibility = View.GONE
    }

    private fun showFloatingActionButton(){
        binding.profileFloatingActionButton.visibility = View.VISIBLE
    }

}