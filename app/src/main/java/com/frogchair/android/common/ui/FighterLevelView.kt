package com.frogchair.android.common.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.frogchair.android.R

class FighterLevelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val progressBar by lazy { findViewById<ProgressBar>(R.id.view_fighter_level_progress_bar) }
    private val maxLevel by lazy { findViewById<TextView>(R.id.view_fighter_level_progress_max) }
    private val currentLevel by lazy { findViewById<TextView>(R.id.view_fighter_level_progress_current) }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_fighter_level, this)
    }

    fun setMaxLevel(max: Int) {
        progressBar.max = max
        maxLevel.text = "/$max"
    }

    fun setCurrentLevel(current: Int) {
        progressBar.progress = current / progressBar.max
        currentLevel.text = current.toString()
    }

}