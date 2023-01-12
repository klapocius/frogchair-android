package com.frogchair.android.common.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.frogchair.android.R


class PrimaryButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val goView: View by lazy { findViewById(R.id.view_primary_button_go) }
    private val lineView: View by lazy { findViewById(R.id.view_primary_button_line) }
    private val resumeView: View by lazy { findViewById(R.id.view_primary_button_resume) }
    private val textView: TextView by lazy { findViewById(R.id.view_primary_text) }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_primary_button, this)
    }

    fun displayGo() {
        goView.visibility = View.VISIBLE
        lineView.visibility = View.GONE
        resumeView.visibility = View.GONE
    }

    fun displayResume() {
        goView.visibility = View.GONE
        lineView.visibility = View.VISIBLE
        resumeView.visibility = View.VISIBLE
        resumeView.background = ContextCompat.getDrawable(context, R.drawable.button_primary_mission)
    }

    fun displayFuse() {
        goView.visibility = View.GONE
        lineView.visibility = View.VISIBLE
        resumeView.visibility = View.VISIBLE
        lineView.background = ContextCompat.getDrawable(context, R.drawable.button_primary_fuse_arrow)
        resumeView.background = ContextCompat.getDrawable(context, R.drawable.button_primary_fuse)
    }

    fun displayJourney() {
        goView.visibility = View.GONE
        lineView.visibility = View.VISIBLE
        resumeView.visibility = View.VISIBLE
        resumeView.background = ContextCompat.getDrawable(context, R.drawable.button_primary_journey)
    }

    fun displayText(text: String) {
        textView.text = text
        textView.visibility = View.VISIBLE
        goView.visibility = View.GONE
        lineView.visibility = View.GONE
        resumeView.visibility = View.GONE
    }
}