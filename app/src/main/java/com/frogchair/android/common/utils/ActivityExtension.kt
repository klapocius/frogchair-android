package com.frogchair.android.common.utils

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

fun <R> ComponentActivity.onResultLauncher(block: (Intent) -> R): ActivityResultLauncher<Intent> {
    return this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            block(result.data!!)
        }
    }
}

fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

inline fun <R> withSDK26(block: () -> R): R? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        block()
    } else {
        null
    }
}

