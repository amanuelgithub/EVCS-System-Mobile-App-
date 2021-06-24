package com.amanuel.evscsystem.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amanuel.evscsystem.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotificationBottomSheetDialog : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notification_bottom_sheet_dialog, container, false)
    }


}