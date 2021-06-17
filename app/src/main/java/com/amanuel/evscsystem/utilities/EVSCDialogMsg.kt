package com.amanuel.evscsystem.utilities

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog

/**
 * Created By: Amanuel Girma
 */

class EVSCDialogMsg {

    // [START MATERIAL DIALOG TEST EXAMPLE]
    /**
     * material dialog can directly be shown or saved to be
     * used at later times
     */
    fun materialDialogText(context: Context): MaterialDialog{
        return MaterialDialog(context).show {
            title(text = "Material Dialog Test")
            message(text = "Testing the Material Dialog Third Party Library")
        }
    }
    // [END MATERIAL DIALOG TEST EXAMPLE]


}