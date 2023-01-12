package com.frogchair.android.common.network

import okhttp3.Interceptor
import okhttp3.Response

class DotInterceptor : Interceptor {

    companion object {
        private const val TOKEN_KEY = "token"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = TokenRepository.getToken()
        val request = chain.request().newBuilder().addHeader(TOKEN_KEY, token).build()
        return chain.proceed(request)
    }
}