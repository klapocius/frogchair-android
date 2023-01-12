package com.frogchair.android.feature.account.login.data

import android.util.Log
import com.frogchair.android.common.model.PlayerData
import com.google.gson.Gson

class RestLoginResponse(
    val message: String,
    val token: String? = null
) {
    fun isTokenExpired() = message == "GOOGLE_AUTH_EXPIRED"
    fun isUserNotFound() = message == "USER_NOT_FOUND"
    fun extractPlayer(): PlayerData? {
        return try {
            Gson().fromJson(message, PlayerData::class.java)
        } catch (e: Exception) {
            Log.w("TAG", "Unable to extract player\n ${e.printStackTrace()}")
            null
        }
    }
}