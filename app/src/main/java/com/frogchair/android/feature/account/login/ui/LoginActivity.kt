package com.frogchair.android.feature.account.login.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.frogchair.android.R
import com.frogchair.android.common.model.PlayerData
import com.frogchair.android.common.network.LocalPlayerRepository
import com.frogchair.android.common.ui.ErrorDialogFragment
import com.frogchair.android.common.utils.onClick
import com.frogchair.android.common.utils.showToast
import com.frogchair.android.feature.account.create.ui.CreateAccountActivity
import com.frogchair.android.feature.account.login.model.LoginViewModel
import com.frogchair.android.feature.home.ui.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException


class LoginActivity : AppCompatActivity() {

    companion object {
        fun navigate(context: Context) = Intent(context, LoginActivity::class.java)
    }

    private val signInButton: SignInButton by lazy { findViewById(R.id.login_sign_in_button) }
    private val toolbarText: TextView by lazy { findViewById(R.id.title_toolbar_text) }
    private val toolbarHomeButton: View by lazy { findViewById(R.id.title_home_button) }

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        toolbarText.text = "WELCOME"
        toolbarHomeButton.visibility = View.GONE

        viewModel.getAccount(this).observe(this, { account ->
            checkGoogleAccount(account.idToken)
        })

        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account == null || account.isExpired) {
            signInButton.setSize(SignInButton.SIZE_WIDE)
            signInButton.onClick {
                viewModel.onStartSignIn(this)
            }
        } else {
            checkGoogleAccount(account.idToken)
        }
    }

    private fun checkGoogleAccount(idToken: String?) {
        try {
            if (idToken != null) {
                viewModel.login(idToken).observe(this) { response ->
                    if (response.response == null) {
                        displayRetryLoginDialog(idToken)
                    } else {
                        when {
                            response.response.isTokenExpired() -> showToast("Token expired. Please try again later")
                            response.response.isUserNotFound() -> goToCreateAccount()
                            response.response.extractPlayer() != null -> goToHome(response.response.extractPlayer()!!)
                            else -> displayRetryLoginDialog(idToken)
                        }
                    }
                }
            } else {
                showToast("Unable to get idToken")
            }
        } catch (e: ApiException) {
            showToast("Login failed, code=" + e.statusCode)
        }
    }

    private fun goToHome(player: PlayerData) {
        LocalPlayerRepository.savePlayer(player)
        startActivity(HomeActivity.navigate(this))
        finish()
    }

    private fun goToCreateAccount() {
        startActivity(CreateAccountActivity.navigate(this))
        finish()
    }

    private fun displayRetryLoginDialog(idToken: String) {
        ErrorDialogFragment.newInstance("Unable to login to server").apply {
            listener = object : ErrorDialogFragment.Listener {
                override fun onRetry() {
                    checkGoogleAccount(idToken)
                }
            }
            show(supportFragmentManager, ErrorDialogFragment.TAG)
        }
    }

}