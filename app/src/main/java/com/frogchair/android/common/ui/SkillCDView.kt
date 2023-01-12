package com.frogchair.android.common.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.frogchair.android.R


class SkillCDView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val colorView: View by lazy { findViewById(R.id.view_skill_cd_color) }
    private val numberView: TextView by lazy { findViewById(R.id.view_skill_cd_number) }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_skill_cd, this)
    }

    fun setCoolDown(cd: Int) {
        if (cd > 4 || cd < 0) {
            return
        }

        numberView.text = cd.toString()

        val backgroundId = when (cd) {
            0 -> R.drawable.ic_proc_ready_no_shadow
            1 -> R.drawable.ic_skill_cd_1
            2 -> R.drawable.ic_skill_cd_2
            else -> R.drawable.ic_skill_cd_3
        }
        colorView.background = ContextCompat.getDrawable(context, backgroundId)
    }

}