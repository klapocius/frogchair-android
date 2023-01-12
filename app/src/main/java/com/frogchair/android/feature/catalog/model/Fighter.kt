package com.frogchair.android.feature.catalog.model

import android.content.Context
import android.content.res.Resources
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fighter(
    val catalogId: String,
    val name: String,

    @SerializedName("class") val fighterClass: Class,
    val rarity: Rarity,
    val tribe: Tribe,
    val sign: Sign,

    val minStats: FighterStats,
    val maxStats: FighterStats,

    val lore: String
) : Parcelable {

    private fun buildImageName() = "fighter_${catalogId}_image"
    fun findImageId(res: Resources, context: Context) =
        res.getIdentifier(buildImageName(), "drawable", context.packageName)

    private fun buildIconName() = "fighter_${catalogId}_icon"
    fun findIconId(res: Resources, context: Context) =
        res.getIdentifier(buildIconName(), "drawable", context.packageName)

    private fun buildPedestralName(rarity: Rarity, tribe: Tribe) = "img_pedestal_" + tribe.name + "_" + rarity.name[0]
    fun findPedestralId(res: Resources, context: Context, rarity: Rarity, tribe: Tribe) =
        res.getIdentifier(buildPedestralName(rarity, tribe), "drawable", context.packageName)

}