package com.frogchair.android.feature.account.login.model

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frogchair.android.R
import com.frogchair.android.common.network.TokenRepository
import com.frogchair.android.common.utils.onResultLauncher
import com.frogchair.android.feature.account.login.data.LoginRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private lateinit var accountLiveData: MutableLiveData<GoogleSignInAccount>
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    fun getAccount(activity: ComponentActivity): LiveData<GoogleSignInAccount> {
        resultLauncher = activity.onResultLauncher { intent ->
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)
            accountLiveData.postValue(task.getResult(ApiException::class.java))
        }

        accountLiveData = MutableLiveData<GoogleSignInAccount>()
        return accountLiveData
    }

    fun onStartSignIn(activity: Activity) {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(activity.getString(R.string.sign_in_client_id))
            .build()
        val googleSignInClient = GoogleSignIn.getClient(activity, signInOptions)
        resultLauncher.launch(googleSignInClient.signInIntent)
    }

    fun login(idToken: String): LiveData<LoginResponse> {
        return MutableLiveData<LoginResponse>().apply {
            viewModelScope.launch(Dispatchers.IO) {
                val result = try {
                    val result = LoginRepository.login(idToken)
                    if (result.token.isNullOrBlank()) {
                        LoginResponse(null, "Unable to get token from server")
                    } else {
                        saveToken(result.token)
                        LoginResponse(result)
                    }
                } catch (e: Exception) {
                    LoginResponse(null, e.message)
                }
                postValue(result)
            }
        }
    }

    private fun saveToken(token: String) {
        TokenRepository.saveToken(token)
    }

}