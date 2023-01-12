package com.frogchair.android.feature.missionlist.domain

import com.frogchair.android.feature.missionlist.data.RestMissionResponse

class MissionResponse(
    val response: RestMissionResponse? = null,
    val error: String? = null
)