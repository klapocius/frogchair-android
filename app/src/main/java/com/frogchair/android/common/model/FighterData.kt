package com.frogchair.android.common.model

import android.content.Context
import android.content.res.Resources
import com.google.gson.annotations.SerializedName

class FighterData(
    val id: Int,
    val currentSef: Int,
    val totalXp: Int,
    val slot: Int,
    val locked: Boolean,
    val tradeable: Boolean,
    val currentCd: Int,
    val name: String,
    val rarity: String,
    val tribe: String,
    val sign: String,
    @SerializedName("class") val job: String,
    val lore: String,
    val tier: String,
    val maxSef: Int,
    val evolutionStep: Int,
    val catalogId: Int,
    val currentXp: Int,
    val levelUpXp: Int,
    val currentLevel: Int,
    val maxLevel: Int,
) {

    private fun buildImageName() = "fighter_${catalogId}_image"
    fun findImageId(res: Resources, context: Context) =
        res.getIdentifier(buildImageName(), "drawable", context.packageName)

    private fun buildAnimName(state: AnimState) = "fighter_${catalogId}_${state.resKey}"
    fun findAnimId(res: Resources, context: Context, state: AnimState) =
        res.getIdentifier(buildAnimName(state), "raw", context.packageName)
}
