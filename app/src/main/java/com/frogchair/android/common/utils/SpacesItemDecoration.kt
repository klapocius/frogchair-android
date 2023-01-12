package com.frogchair.android.common.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

class SpaceItemDecoration(
    val context: WeakReference<Context>,
    @DimenRes val spaceDimenRes: Int,
    var columnCount: Int
) : RecyclerView.ItemDecoration() {

    private val spaceDimenPx: Int by lazy {
        context.get()?.resources?.getDimensionPixelSize(spaceDimenRes) ?: -1
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val itemPosition: Int = parent.getChildAdapterPosition(view)
        when (itemPosition) {
            0 -> outRect.bottom = spaceDimenPx * 2 //header
            parent.adapter?.itemCount?.minus(1) -> outRect.top = spaceDimenPx * 2 //footer
            else -> {
                if (columnCount < 0) { //columns disabled
                    outRect.left = (spaceDimenPx * 0.5).toInt()
                    outRect.right = (spaceDimenPx * 0.5).toInt()
                    outRect.top = spaceDimenPx
                    return
                }

                when ((itemPosition - 1) % columnCount) { //current column
                    0 -> outRect.left = (spaceDimenPx * 1.5).toInt()
                    1 -> {
                        outRect.left = spaceDimenPx
                        outRect.right = (spaceDimenPx * 0.5).toInt()
                    }
                    2 -> {
                        outRect.right = spaceDimenPx
                        outRect.left = (spaceDimenPx * 0.5).toInt()
                    }
                    3 -> outRect.right = (spaceDimenPx * 1.5).toInt()
                }
                outRect.top = spaceDimenPx
            }
        }
    }
}