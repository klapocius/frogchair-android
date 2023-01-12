package com.frogchair.android.feature.more

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.frogchair.android.R


class MoreMenuItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val imageView by lazy { findViewById<View>(R.id.view_more_menu_image) }
    private val textView by lazy { findViewById<TextView>(R.id.view_more_menu_text) }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_more_menu_item, this)
        attrs?.let {
            loadAttrs(context, it)
        }
    }

    private fun loadAttrs(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MoreMenuItemView, 0, 0)
        try {
            typedArray.getDrawable(R.styleable.MoreMenuItemView_moreImage)?.let { drawable ->
                imageView.background = drawable
            }
            typedArray.getFloat(R.styleable.MoreMenuItemView_moreAlpha, 1f).let { alpha ->
                imageView.alpha = alpha
            }
            typedArray.getString(R.styleable.MoreMenuItemView_moreText)?.let { text ->
                textView.text = text
            }

        } finally {
            typedArray.recycle()
        }
    }

}