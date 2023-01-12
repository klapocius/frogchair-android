package com.frogchair.android.common.ui

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.frogchair.android.R
import com.frogchair.android.common.utils.TimeUtils


class NuxRegenView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val MAX_SEC = 1.5 * 60 * 60 //1h30
        private const val ONE_NUX_TIME = (MAX_SEC / 3f).toInt()
    }

    private val timerView by lazy { findViewById<TextView>(R.id.view_nux_regen_timer) }
    private val bp1View by lazy { findViewById<View>(R.id.view_nux_regen_bp_1) }
    private val bp2View by lazy { findViewById<View>(R.id.view_nux_regen_bp_2) }
    private val bp3View by lazy { findViewById<View>(R.id.view_nux_regen_bp_3) }

    private val nuxBitDrawable by lazy { ContextCompat.getDrawable(context, R.drawable.ic_nux_bit) }
    private val emptyNuxBitDrawable by lazy { ContextCompat.getDrawable(context, R.drawable.ic_nux_bit_empty) }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_nux_regen, this)
    }

    fun init(milliSecToFull: Long) {
        updateTimerView(milliSecToFull)
        updateNuxViews(milliSecToFull)
        startTimer(milliSecToFull)
    }

    private fun updateNuxViews(milliSecToFull: Long) {
        val nuxBits = (MAX_SEC - milliSecToFull / 1000) / ONE_NUX_TIME
        bp1View.background = if (nuxBits >= 1) nuxBitDrawable else emptyNuxBitDrawable
        bp2View.background = if (nuxBits >= 2) nuxBitDrawable else emptyNuxBitDrawable
        bp3View.background = if (nuxBits >= 3) nuxBitDrawable else emptyNuxBitDrawable
    }

    private fun updateTimerView(milliSecToFull: Long) {
        if (milliSecToFull == 0L) {
            timerView.visibility = GONE
            return
        } else {
            timerView.visibility = VISIBLE
        }
        timerView.text = TimeUtils.formatHour(milliSecToFull)
    }

    private fun startTimer(milliSecToFull: Long) {
        object : CountDownTimer(milliSecToFull, 1000) {
            override fun onTick(duration: Long) {
                updateTimerView(duration)
                updateNuxViews(duration)
            }

            override fun onFinish() {
                updateTimerView(0)
                updateNuxViews(0)
            }
        }.start()
    }
}