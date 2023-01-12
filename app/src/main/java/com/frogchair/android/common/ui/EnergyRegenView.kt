package com.frogchair.android.common.ui

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.frogchair.android.R
import com.frogchair.android.common.utils.TimeUtils


class EnergyRegenView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val MAX_SEC = 5f * 60 * 60 //5 hours
        private const val MAX_ENERGY = 100
    }

    private val timerView by lazy { findViewById<TextView>(R.id.view_energy_regen_timer) }
    private val currentEnergyView by lazy { findViewById<TextView>(R.id.view_energy_regen_progress_text) }
    private val progressBar by lazy { findViewById<ProgressBar>(R.id.view_energy_regen_progress_bar) }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_energy_regen, this)
    }

    fun init(milliSecToFull: Long) {
        updateTimerView(milliSecToFull)
        updateProgress(milliSecToFull)
        startTimer(milliSecToFull)
    }

    private fun updateProgress(milliSecToFull: Long) {
        progressBar.max = MAX_SEC.toInt()
        val progress = MAX_SEC - milliSecToFull / 1000
        progressBar.progress = progress.toInt()
        currentEnergyView.text = (progress / MAX_SEC * MAX_ENERGY).toInt().toString()
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
                updateProgress(duration)
            }

            override fun onFinish() {
                updateTimerView(0)
                updateProgress(0)
            }
        }.start()
    }

}