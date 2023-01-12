package com.frogchair.android.feature.catalog.model

import com.frogchair.android.R

//lowercase needed for gson serialization
enum class Sign(val resId: Int, val colorId: Int) {
    air(R.drawable.ic_sign_air, R.color.fighter_stats_air),
    earth(R.drawable.ic_sign_earth, R.color.fighter_stats_earth),
    fire(R.drawable.ic_sign_fire, R.color.fighter_stats_fire),
    lightning(R.drawable.ic_sign_lightning, R.color.fighter_stats_lightning),
    water(R.drawable.ic_sign_water, R.color.fighter_stats_water)
}

enum class Tribe(val resId: Int, val colorId: Int) {
    hemi(R.drawable.ic_tribe_hemi, R.color.fighter_stats_hemi),
    theri(R.drawable.ic_tribe_theri, R.color.fighter_stats_theri),
    xana(R.drawable.ic_tribe_xana, R.color.fighter_stats_xana)
}

enum class Class(val resId: Int) {
    champ(R.drawable.ic_class_champ),
    guru(R.drawable.ic_class_guru),
    rogue(R.drawable.ic_class_rogue),
    scout(R.drawable.ic_class_scout),
    warlock(R.drawable.ic_class_warlock)
}

enum class Rarity(val resId: Int, val resIdBig: Int) {
    common(R.drawable.ic_rarity_common, R.drawable.ic_rarity_common_big),
    uncommon(R.drawable.ic_rarity_uncommon, R.drawable.ic_rarity_uncommon_big),
    rare(R.drawable.ic_rarity_rare, R.drawable.ic_rarity_rare_big),
    epic(R.drawable.ic_rarity_epic, R.drawable.ic_rarity_epic_big),
    legendary(R.drawable.ic_rarity_legend, R.drawable.ic_rarity_legend_big)
}