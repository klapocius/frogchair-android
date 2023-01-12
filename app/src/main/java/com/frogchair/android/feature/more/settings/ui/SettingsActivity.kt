package com.frogchair.android.feature.more.settings.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.frogchair.android.R
import com.frogchair.android.common.utils.onClick
import com.frogchair.android.common.utils.showToast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class SettingsActivity : AppCompatActivity() {

    companion object {
        fun navigate(context: Context) = Intent(context, SettingsActivity::class.java)
    }

    private val signOutButton: View by lazy { findViewById(R.id.settings_logout) }
    private val toolbarText: TextView by lazy { findViewById(R.id.title_toolbar_text) }
    private val toolbarHomeButton: View by lazy { findViewById(R.id.title_home_button) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        toolbarText.text = "SETTINGS"
        toolbarHomeButton.visibility = View.GONE

        signOutButton.onClick { logout() }
    }

    private fun logout() {
        showToast("Logout")
//        TokenRepository.saveToken("")
//        signOutGoogle()
//
//        val intent = LoginActivity.navigate(this)
//        intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(intent)
//        finish()
    }

    private fun signOutGoogle() {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.sign_in_client_id))
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, signInOptions)
        googleSignInClient.signOut()
    }

}