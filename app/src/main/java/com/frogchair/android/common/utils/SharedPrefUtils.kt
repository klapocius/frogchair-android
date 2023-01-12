package com.frogchair.android.common.utils

import android.app.Activity
import androidx.core.content.edit
import com.frogchair.android.common.DotApplication

object SharedPrefUtils {

    private const val PREF_FILE = "prefs"

    private fun getSharedPrefs() = DotApplication.getContext().getSharedPreferences(PREF_FILE, Activity.MODE_PRIVATE)

    fun getInt(key: String, defValue: Int = -1) = getSharedPrefs().getInt(key, defValue)

    fun saveInt(key: String, value: Int) {
        getSharedPrefs().edit {
            putInt(key, value)
        }
    }

    fun getString(key: String, defValue: String = "") = getSharedPrefs().getString(key, defValue) ?: defValue

    fun saveString(key: String, value: String) {
        getSharedPrefs().edit {
            putString(key, value)
        }
    }

}