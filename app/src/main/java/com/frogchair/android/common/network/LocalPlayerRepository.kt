package com.frogchair.android.common.network

import com.frogchair.android.common.model.PlayerData
import com.frogchair.android.common.utils.SharedPrefUtils
import com.google.gson.Gson

object LocalPlayerRepository {
    private const val PLAYER_KEY = "player"

    private var cachedPlayer: PlayerData? = null

    fun getPlayer(): PlayerData {
        if (cachedPlayer == null) {
            cachedPlayer = getLocalPlayer()
        }
        return cachedPlayer!!
    }


//    private fun getLocalPlayer() = Gson().fromJson(SharedPrefUtils.getString(PLAYER_KEY), PlayerData::class.java)

    private fun getLocalPlayer() = Gson().fromJson(
        "{\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"string\",\n" +
                "    \"jwtToken\": \"string\",\n" +
                "    \"class\": 0,\n" +
                "    \"currentXp\": 0,\n" +
                "    \"classUpXp\": 0,\n" +
                "    \"bpCountDown\": 0,\n" +
                "    \"enCountDown\": 0,\n" +
                "    \"crowns\": [\n" +
                "      {\n" +
                "        \"type\": \"gold\",\n" +
                "        \"count\": 0\n" +
                "      }\n" +
                "    ],\n" +
                "    \"pfpUrl\": \"string\",\n" +
                "    \"currentProgress\": 0,\n" +
                "    \"currentMission\": 0,\n" +
                "    \"pavilion\": [\n {'id': 0,'catalogId': 104412,'totalXp': 0,'currentXp': 0,'levelUpXp': 0,'currentLevel': 0,'maxLevel': 0,'currentSef': 0,'currentCd': 0,'locked': true,'slot': 0}," +
                "{'id': 0,'catalogId': 207302,'totalXp': 0,'currentXp': 0,'levelUpXp': 0,'currentLevel': 0,'maxLevel': 0,'currentSef': 0,'currentCd': 0,'locked': true,'slot': 0}," +
                "{'id': 0,'catalogId': 212002,'totalXp': 0,'currentXp': 0,'levelUpXp': 0,'currentLevel': 0,'maxLevel': 0,'currentSef': 0,'currentCd': 0,'locked': true,'slot': 0}," +
                "{'id': 0,'catalogId': 104002,'totalXp': 0,'currentXp': 0,'levelUpXp': 0,'currentLevel': 0,'maxLevel': 0,'currentSef': 0,'currentCd': 0,'locked': true,'slot': 0}," +
                "{'id': 0,'catalogId': 315202,'totalXp': 0,'currentXp': 0,'levelUpXp': 0,'currentLevel': 0,'maxLevel': 0,'currentSef': 0,'currentCd': 0,'locked': true,'slot': 0}," +
                "{'id': 0,'catalogId': 106102,'totalXp': 0,'currentXp': 0,'levelUpXp': 0,'currentLevel': 0,'maxLevel': 0,'currentSef': 0,'currentCd': 0,'locked': true,'slot': 0}," +
                "{'id': 0,'catalogId': 108102,'totalXp': 0,'currentXp': 0,'levelUpXp': 0,'currentLevel': 0,'maxLevel': 0,'currentSef': 0,'currentCd': 0,'locked': true,'slot': 0}," +
                "{'id': 0,'catalogId': 117102,'totalXp': 0,'currentXp': 0,'levelUpXp': 0,'currentLevel': 0,'maxLevel': 0,'currentSef': 0,'currentCd': 0,'locked': true,'slot': 0}," +
                "{'id': 0,'catalogId': 305302,'totalXp': 0,'currentXp': 0,'levelUpXp': 0,'currentLevel': 0,'maxLevel': 0,'currentSef': 0,'currentCd': 0,'locked': true,'slot': 0}" +
                "    ]\n" +
                "  }", PlayerData::class.java
    )

    fun savePlayer(player: PlayerData) {
        cachedPlayer = player
        SharedPrefUtils.saveString(PLAYER_KEY, Gson().toJson(player))
    }

}