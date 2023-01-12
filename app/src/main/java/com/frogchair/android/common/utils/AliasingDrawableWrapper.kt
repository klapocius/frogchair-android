package com.frogchair.android.common.utils

import android.graphics.Canvas
import android.graphics.DrawFilter
import android.graphics.Paint
import android.graphics.PaintFlagsDrawFilter
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableWrapper
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.M)
class AliasingDrawableWrapper(wrapped: Drawable?) : DrawableWrapper(wrapped) {

    companion object {
        private val DRAW_FILTER: DrawFilter = PaintFlagsDrawFilter(Paint.FILTER_BITMAP_FLAG, 0)
    }

    override fun draw(canvas: Canvas) {
        val oldDrawFilter = canvas.drawFilter
        canvas.drawFilter = DRAW_FILTER
        super.draw(canvas)
        canvas.drawFilter = oldDrawFilter
    }
}