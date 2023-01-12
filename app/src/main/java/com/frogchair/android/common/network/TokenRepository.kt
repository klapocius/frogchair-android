package com.frogchair.android.common.network

import com.frogchair.android.common.utils.SharedPrefUtils

object TokenRepository {
    private const val TOKEN_KEY = "token"

    var cachedToken: String? = null

    fun getToken(): String {
        if (cachedToken.isNullOrEmpty()) {
            cachedToken = getLocalToken()
        }
        return cachedToken!!
    }

    private fun getLocalToken() = SharedPrefUtils.getString(TOKEN_KEY)

    fun saveToken(token: String) {
        cachedToken = token
        SharedPrefUtils.saveString(TOKEN_KEY, token)
    }

}