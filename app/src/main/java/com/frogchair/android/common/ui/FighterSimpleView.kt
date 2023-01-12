package com.frogchair.android.common.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import com.frogchair.android.R
import com.frogchair.android.common.utils.setDrawableAliasing
import com.frogchair.android.feature.catalog.model.Fighter

class FighterSimpleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val rarityView by lazy { findViewById<View>(R.id.view_fighter_simple_rarity) }
    private val signView by lazy { findViewById<View>(R.id.view_fighter_simple_sign) }

    private val fighterImage by lazy { findViewById<ImageView>(R.id.view_fighter_simple_image) }
    private val dispensableViews by lazy { findViewById<Group>(R.id.view_fighter_dispensable_views) }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_fighter_simple, this)
    }

    fun init(fighter: Fighter) {
        fighterImage.setDrawableAliasing(fighter.findIconId(resources, context))
        rarityView.background = ContextCompat.getDrawable(context, fighter.rarity.resIdBig)
        signView.background = ContextCompat.getDrawable(context, fighter.sign.resId)
    }

    fun displayOnlyRarityAndSign() {
        dispensableViews.visibility = View.GONE
    }

    fun displayOnlyRarity() {
        signView.visibility = View.GONE
        dispensableViews.visibility = View.GONE
    }

}