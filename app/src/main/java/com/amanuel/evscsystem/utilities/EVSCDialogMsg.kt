package com.amanuel.evscsystem.utilities

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.amanuel.evscsystem.R

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
    }

}