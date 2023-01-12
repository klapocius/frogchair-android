package com.frogchair.android.common.model

import com.google.gson.annotations.SerializedName

class PlayerData(
    val id: Int,
    val name: String,
    val email: String,
    val token: String,
    @SerializedName("class") val userClass: Int,
    val currentXp: Int,
    val classUpXp: Int,
    val bpCountdown: Long, // milliseconds to full
    val enCountdown: Long,
    val currentProgress: Int,
    val currentMission: Int,
    val crowns: List<Crown>?,
    val pfpUrl: String,
    val pavilion: List<FighterData>
)

class Crown(
    val type: CrownType,
    val amount: Int
)

enum class CrownType(value: String) {
    GOLD("gold"),
    SILVER("silver"),
    BRONZE("bronze")
}
