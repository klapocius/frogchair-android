package com.frogchair.android.feature.splashscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.frogchair.android.R
import com.frogchair.android.common.utils.showToast
import com.frogchair.android.feature.account.login.ui.LoginActivity
import com.frogchair.android.feature.home.ui.HomeActivity

class SplashActivity : AppCompatActivity() {

    companion object {
        fun navigate(context: Context) = Intent(context, SplashActivity::class.java)
    }

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

//        if (TokenRepository.getToken().isEmpty()) {
//            goToCreateAccount()
//        } else {
//            fetchPlayerData()
//        }
        goToHome()

    }

    private fun fetchPlayerData() {
        viewModel.getPlayer().observe(this, {
            if (it != null) {
                goToHome()
            } else {
                showToast("Unable to fetch player data")
            }
        })
    }

    private fun goToHome() {
        startActivity(HomeActivity.navigate(this@SplashActivity))
        finish()
    }

    private fun goToCreateAccount() {
        startActivity(LoginActivity.navigate(this@SplashActivity))
        finish()
    }


}