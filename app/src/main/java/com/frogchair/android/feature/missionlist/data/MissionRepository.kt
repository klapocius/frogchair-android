package com.frogchair.android.feature.missionlist.data

import com.frogchair.android.common.network.RestClient
import com.frogchair.android.feature.missionlist.domain.Mission
import retrofit2.http.GET

typealias RestMissionResponse = List<Mission>

object MissionRepository {

    private val retrofitService: RetrofitService = RestClient.createService(RetrofitService::class.java)

    //    suspend fun getMissions() = retrofitService.getMissions()
    suspend fun getMissions() = listOf(chapter1, chapter2)

    private val submission1 = Mission(1, "1", "Mission", "completed", null, null, 20, "")
    private val submission2 = Mission(2, "2", "Mission", "completed", null, null, 20, "")
    private val submission3 = Mission(3, "3", "Mission", "locked", null, null, 20, "")
    private val mission1 =
        Mission(1, "1", "A small beginning", "completed", null, listOf(submission1, submission2, submission3), null, "")
    private val mission2 =
        Mission(2, "2", "Big adventure", "completed", null, listOf(submission1, submission2, submission3), null, "")
    private val chapter1 = Mission(1, "1", "Chapter1", "completed", null, listOf(mission1, mission2), null, "")
    private val chapter2 = Mission(1, "1", "Chapter2", "completed", null, listOf(mission1, mission2), null, "")

    interface RetrofitService {
        @GET("missions/event/1")
        suspend fun getMissions(): RestMissionResponse
    }

}