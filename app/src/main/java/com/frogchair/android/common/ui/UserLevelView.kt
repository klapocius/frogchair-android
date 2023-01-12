package com.frogchair.android.common.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.frogchair.android.R


class UserLevelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val levelView by lazy { findViewById<TextView>(R.id.view_user_level_text) }
    private val experienceLeftView by lazy { findViewById<TextView>(R.id.view_user_level_progress_text) }
    private val progressBar by lazy { findViewById<ProgressBar>(R.id.view_user_level_progress_bar) }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_user_level, this)
    }

    fun init(level: Int, currentXp: Int, maxXp: Int) {
        levelView.text = level.toString()
        experienceLeftView.text = (maxXp - currentXp).toString()
        progressBar.max = maxXp
        progressBar.progress = currentXp
    }
}