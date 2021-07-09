package com.amanuel.evscsystem.ui.report

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.databinding.FragmentReportBinding


class ReportFragment : Fragment(R.layout.fragment_report) {

    private lateinit var binding: FragmentReportBinding

//    private lateinit var violationItems: ArrayList<String>
//    private var checkedItems: BooleanArray? = null
//    private var selectedItems = ArrayList<Int>()

    private lateinit var arrayViolations: Array<String>
    private lateinit var arrayChecked: BooleanArray


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentReportBinding.bind(view)

        arrayViolations = resources.getStringArray(R.array.other_violations_array)
        arrayChecked = BooleanArray(arrayViolations.size)

        binding.otherViolationsTextView.setOnClickListener {
            showDialog()
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