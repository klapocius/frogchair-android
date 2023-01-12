package com.frogchair.android.feature.catalog.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FighterStats(
    val hp: Int,
    val atk: Int,
    val def: Int,
    val wis: Int,
    val agi: Int
) : Parcelable