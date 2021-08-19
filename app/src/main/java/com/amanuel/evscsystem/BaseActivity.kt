package com.amanuel.evscsystem

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import com.amanuel.evscsystem.data.SessionManager

open class BaseActivity: AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
// get chosen language from shread preference
        val localeToSwitchTo = SessionManager(newBase).getAppLanguage()
        val localeUpdatedContext: ContextWrapper = ContextUtils.updateLocale(newBase, localeToSwitchTo)
        super.attachBaseContext(localeUpdatedContext)
    }

}