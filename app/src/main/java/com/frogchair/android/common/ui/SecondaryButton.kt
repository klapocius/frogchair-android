package com.frogchair.android.common.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.frogchair.android.R


class SecondaryButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val iconView by lazy { findViewById<View>(R.id.view_secondary_button_icon) }
    private val textView by lazy { findViewById<TextView>(R.id.view_secondary_button_text) }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_secondary_button, this)
        attrs?.let {
            loadAttrs(context, it)
        }
    }

    private fun loadAttrs(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SecondaryButton, 0, 0)
        try {
            typedArray.getDrawable(R.styleable.SecondaryButton_iconDrawable)?.let { drawable ->
                setIcon(drawable)
            }
            typedArray.getString(R.styleable.SecondaryButton_text)?.let { text ->
                setText(text)
            }

        } finally {
            typedArray.recycle()
        }
    }

    fun setIcon(drawable: Drawable) {
        iconView.background = drawable
    }

    fun setText(text: String) {
        textView.text = text
    }

}