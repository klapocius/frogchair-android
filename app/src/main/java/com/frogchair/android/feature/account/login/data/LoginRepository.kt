package com.frogchair.android.feature.account.login.data

import com.frogchair.android.common.network.RestClient
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Path

object LoginRepository {

    private val retrofitService: RetrofitService = RestClient.createService(RetrofitService::class.java)

    suspend fun login(idToken: String): RestLoginResponse {
        val result = retrofitService.login(idToken)
        val jsonObject = JSONObject(result)
        return RestLoginResponse(
            message = jsonObject.getString("message"),
            token = jsonObject.getString("token")
        )
    }

    interface RetrofitService {
        @GET("login/{idToken}")
        suspend fun login(@Path("idToken") idToken: String): String
    }

}