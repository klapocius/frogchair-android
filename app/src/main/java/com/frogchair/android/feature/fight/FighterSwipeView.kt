package com.frogchair.android.feature.fight

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.frogchair.android.R
import com.frogchair.android.common.utils.setDrawableAliasing

class FighterSwipeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val overlayView by lazy { findViewById<View>(R.id.view_fighter_frame_overlay) }
    private val statsGroup by lazy { findViewById<View>(R.id.view_fighter_frame_stats_group) }
    private val skillProc by lazy { findViewById<View>(R.id.view_fighter_frame_skill_proc) }
    private val skillName by lazy { findViewById<View>(R.id.view_fighter_frame_skill_name) }
    private val skillType by lazy { findViewById<View>(R.id.view_fighter_frame_skill_type) }

    private val fighterImage by lazy { findViewById<ImageView>(R.id.view_figter_frame_image) }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_fighter_swipe, this)

        fighterImage.setDrawableAliasing(R.drawable.fighter_205402_icon)
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

    private fun displayOverlay() {
        overlayView.visibility = VISIBLE
    }

    private fun hideOverlay() {
        overlayView.visibility = GONE
    }
}