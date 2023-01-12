package com.frogchair.android.feature.account.login.model

import com.frogchair.android.feature.account.login.data.RestLoginResponse

class LoginResponse(
    val response: RestLoginResponse? = null,
    val error: String? = null
)
