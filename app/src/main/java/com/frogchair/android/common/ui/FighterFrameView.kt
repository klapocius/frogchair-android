package com.frogchair.android.common.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.Group
import com.frogchair.android.R
import com.frogchair.android.common.utils.onClick
import com.frogchair.android.common.utils.setDrawableAliasing

class FighterFrameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    interface Listener {
        fun onFighterClicked()
        fun onWeaponClicked()
    }

    private val overlayView by lazy { findViewById<View>(R.id.view_fighter_frame_overlay) }
    private val overlayWeaponView by lazy { findViewById<View>(R.id.view_fighter_frame_overlay_weapon) }
    private val statsGroup by lazy { findViewById<View>(R.id.view_fighter_frame_stats_group) }
    private val skillProc by lazy { findViewById<View>(R.id.view_fighter_frame_skill_proc) }
    private val skillName by lazy { findViewById<View>(R.id.view_fighter_frame_skill_name) }
    private val skillType by lazy { findViewById<View>(R.id.view_fighter_frame_skill_type) }

    private val rarityImage by lazy { findViewById<View>(R.id.view_fighter_frame_rarity) }
    private val fighterImage by lazy { findViewById<ImageView>(R.id.view_figter_frame_image) }
    private val weaponImage by lazy { findViewById<View>(R.id.view_fighter_frame_weapon_image) }

    private val weaponGroup by lazy { findViewById<Group>(R.id.view_fighter_frame_weapon_group) }
    private val levelGroup by lazy { findViewById<Group>(R.id.view_fighter_frame_level_group) }

    private var listener: Listener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_fighter_frame, this)

        fighterImage.onClick { listener?.onFighterClicked() }
        weaponImage.onClick { listener?.onWeaponClicked() }

        fighterImage.setDrawableAliasing(R.drawable.fighter_205402_icon)
    }

    fun setListener(l: Listener) {
        listener = l
    }

    fun hideStats() {
        hideOverlay()
        statsGroup.visibility = GONE
    }

    fun displayStats() {
        displayOverlay()
        statsGroup.visibility = VISIBLE
    }


    fun hideSkills() {
        hideOverlay()
        skillProc.visibility = GONE
        skillType.visibility = GONE
        skillName.visibility = GONE
    }

    fun displaySkills() {
        displayOverlay()
        skillProc.visibility = VISIBLE
        skillType.visibility = VISIBLE
        skillName.visibility = VISIBLE
    }

    fun hideLevelView() {
        levelGroup.visibility = GONE
    }

    fun hideRarity() {
        rarityImage.visibility = GONE
    }

    fun hideWeapon() {
        weaponGroup.visibility = GONE
    }

    private fun displayOverlay() {
        overlayView.visibility = VISIBLE
        overlayWeaponView.visibility = VISIBLE
    }

    private fun hideOverlay() {
        overlayView.visibility = GONE
        overlayWeaponView.visibility = GONE
    }
}