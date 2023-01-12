package com.frogchair.android.feature.account.create.data

import com.frogchair.android.common.model.PlayerData
import com.frogchair.android.common.network.RestClient
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

object RemotePlayerRepository {

    private val retrofitService: RetrofitService = RestClient.createLoggedService(RetrofitService::class.java)

    suspend fun create(username: String, profilePicture: String): RestAccountResponse {
        return retrofitService.create(
            mapOf(
                "name" to username,
                "profilePicture" to profilePicture
            ),
        )
    }

    suspend fun getPlayer() = retrofitService.get()

    interface RetrofitService {
        @PATCH("player")
        suspend fun create(@Body body: Map<String, String>): RestAccountResponse

        @GET("player")
        suspend fun get(): PlayerData
    }

}