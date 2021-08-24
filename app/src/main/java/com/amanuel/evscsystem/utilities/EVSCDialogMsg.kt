package com.amanuel.evscsystem.utilities

import android.R
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.afollestad.materialdialogs.MaterialDialog


/**
 * Created By: Amanuel Girma
 */

class EVSCDialogMsg {

    companion object {
        // [START MATERIAL DIALOG TEST EXAMPLE]
        /**
         * material dialog can directly be shown or saved to be
         * used at later times
         */
        fun materialDialogText(context: Context): MaterialDialog {
            return MaterialDialog(context).show {
                title(text = "Material Dialog Test")
                message(text = "Testing the Material Dialog Third Party Library")
            }
        }
        // [END MATERIAL DIALOG TEST EXAMPLE]

        // a dialog that will be shown when the other_violations_text_view is clicked
        fun otherViolationsDialog(context: Context): MaterialDialog {
            return MaterialDialog(context)
        }

        fun showSuccessAlert(
            context: Context?,
            heading: String?, description: String?,
            action: DialogInterface.OnClickListener?
        ) {
            AlertDialog.Builder(context!!).setTitle(heading)
                .setMessage(description)
                .setPositiveButton(R.string.ok, action)
                .setIcon(R.drawable.ic_dialog_info).show()
        }
    }

    fun ShowSuccessAlert(
        context: Context,
        heading: String?, description: String?
    ) {
        AlertDialog.Builder(context)
            .setTitle(heading) // w w w.ja  va  2  s . co  m
            .setMessage(description)
            .setPositiveButton(android.R.string.yes,
                DialogInterface.OnClickListener { dialog, which ->
                    if (!(context as Activity).isFinishing) context.finish()
                }
            )
            .setIcon(android.R.drawable.ic_dialog_info)
            .show()
    }



}