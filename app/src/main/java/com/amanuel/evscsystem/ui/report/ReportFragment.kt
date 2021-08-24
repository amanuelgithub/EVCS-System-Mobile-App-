package com.amanuel.evscsystem.ui.report

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.data.db.models.Report
import com.amanuel.evscsystem.databinding.FragmentReportBinding
import com.amanuel.evscsystem.utilities.EVSCDialogMsg
import com.amanuel.evscsystem.utilities.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class ReportFragment : Fragment(R.layout.fragment_report) {

    private lateinit var binding: FragmentReportBinding

    private val viewModel: ReportViewModel by viewModels()

    private var recordId by Delegates.notNull<Int>()
    private var plateNumber by Delegates.notNull<String>()

//    private lateinit var violationItems: ArrayList<String>
//    private var checkedItems: BooleanArray? = null
//    private var selectedItems = ArrayList<Int>()

    private lateinit var arrayViolations: Array<String>
    private lateinit var arrayChecked: BooleanArray


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentReportBinding.bind(view)

        val args = ReportFragmentArgs.fromBundle(requireArguments())
        recordId = args.recordId
        plateNumber = args.plateNumber

        Toast.makeText(requireActivity(), "Record: $recordId", Toast.LENGTH_SHORT).show()

        arrayViolations = resources.getStringArray(R.array.other_violations_array)
        arrayChecked = BooleanArray(arrayViolations.size)

        binding.otherViolationsTextView.setOnClickListener {
            showDialog()
        }


        // handle the click event of the report button
        binding.reportButton.setOnClickListener {
            report()
        }

    }

    private fun report() {
        val isDrunk: Boolean = binding.isDrunkCheckBox.isChecked
        val isUsingCellPhone: Boolean = binding.isUsingCellPhoneCheckBox.isChecked
        val isNotHavingLicense: Boolean = binding.isNotHavingLicenceCheckBox.isChecked
        val isChewingChat: Boolean = binding.isChewingChatCheckBox.isChecked
        val otherViolations: String = binding.otherViolationsTextView.text.toString()
        val description: String = binding.descriptionEditTextTextMultiLine.text.toString()

        var paymentAmount: Double
        if (binding.paymentAmountTextField.text.toString().isNotEmpty()) {
            paymentAmount = binding.paymentAmountTextField.text.toString().toDouble()
        } else {
            paymentAmount = 0.0
        }

        val shortSummary: String = binding.shortSummaryEditTextTextMultiLine.text.toString()

        val report = Report(
            isDrunk,
            isUsingCellPhone,
            isNotHavingLicense,
            isChewingChat,
            otherViolations,
            description,
            paymentAmount,
            shortSummary
        )

        view?.let {
            viewModel.report(it, recordId, report) { report ->
                if (report != null) {
                    // it = newly added user parsed as response
                    // it?.id = newly added user ID
                    Toast.makeText(requireContext(), "reporting is success!", Toast.LENGTH_SHORT).show()

                    EVSCDialogMsg.showSuccessAlert(
                        requireContext(),
                        "Reporting Success!!!",
                        "You have successfully written a report for vehicle with plate number: $plateNumber"
                    ) { dialog, which ->
                        findNavController().navigate(R.id.action_reportFragment_to_notificationsFragment)
                    }

                } else {
                    view?.showErrorSnackBar("Reporting Failure!!!")
                }
            }
        }
    }


    // Method to show an alert dialog with multiple choice list items
    private fun showDialog() {
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog

        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(requireContext())

        // Set a title for alert dialog
        builder.setTitle("Choose favorite colors.")

        // Define multiple choice items for alert dialog
        builder.setMultiChoiceItems(arrayViolations, arrayChecked) { dialog, which, isChecked ->
            // Update the clicked item checked status
            arrayChecked[which] = isChecked

            // Get the clicked item
            val violation = arrayViolations[which]

            // Display the clicked item text
            Toast.makeText(requireContext(), "$violation clicked.", Toast.LENGTH_SHORT).show()
        }

        // Set the positive/yes button click listener
        builder.setPositiveButton("OK") { _, _ ->
            // Do something when click positive button
            binding.otherViolationsTextView.text = ""
            for (i in 0 until arrayViolations.size) {
                val checked = arrayChecked[i]
                if (checked) {
                    binding.otherViolationsTextView.text =
                        "${binding.otherViolationsTextView.text}  ${arrayViolations[i]} \n"
                } else {
                    arrayChecked[i] = false
                }
            }
        }
        // Initialize the AlertDialog using builder object
        dialog = builder.create()
        // Finally, display the alert dialog
        dialog.show()
    }

}