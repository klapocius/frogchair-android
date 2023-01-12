package com.frogchair.android.feature.fight

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams
import com.frogchair.android.R
import com.frogchair.android.feature.fight.CurrentLine.*
import kotlin.math.min

class SwipeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    interface Listener {
        fun onSwipeFinished(lineInfo1: LineInfo, lineInfo2: LineInfo, lineInfo3: LineInfo)
    }

    var listener: Listener? = null

    private val touchDownView: View by lazy { findViewById(R.id.view_swipe_touch_down) }

    private val horizontal1View: View by lazy { findViewById(R.id.view_swipe_line_horizontal_1) }
    private val horizontal2View: View by lazy { findViewById(R.id.view_swipe_line_horizontal_2) }
    private val horizontal3View: View by lazy { findViewById(R.id.view_swipe_line_horizontal_3) }
    private val vertical1View: View by lazy { findViewById(R.id.view_swipe_line_vertical_1) }
    private val vertical2View: View by lazy { findViewById(R.id.view_swipe_line_vertical_2) }
    private val vertical3View: View by lazy { findViewById(R.id.view_swipe_line_vertical_3) }
    private val upwardsView: View by lazy { findViewById(R.id.view_swipe_line_upwards) }
    private val downwardsView: View by lazy { findViewById(R.id.view_swipe_line_downwards) }

    private val fighterViewList: List<FighterSwipeView> by lazy {
        listOf(
            findViewById(R.id.swipe_fighter_1),
            findViewById(R.id.swipe_fighter_2),
            findViewById(R.id.swipe_fighter_3),
            findViewById(R.id.swipe_fighter_4),
            findViewById(R.id.swipe_fighter_5),
            findViewById(R.id.swipe_fighter_6),
            findViewById(R.id.swipe_fighter_7),
            findViewById(R.id.swipe_fighter_8),
            findViewById(R.id.swipe_fighter_9)
        )
    }

    var currentLine = L1
    var touchDownFighterColumn = 0
    var touchDownFighterRow = 0
    var line1Info: LineInfo? = null
    var line2Info: LineInfo? = null
    var line3Info: LineInfo? = null

    var selectingLineView: View? = null

    var allowSwipe = true

    init {
        LayoutInflater.from(context).inflate(R.layout.view_swipe, this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!allowSwipe) return false

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                updateTouchDownView(event.x, event.y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                handleSwipe(event.x, event.y)
            }
            MotionEvent.ACTION_UP -> {
                validateLine()
            }
        }
        return super.onTouchEvent(event)
    }

    private fun updateTouchDownView(x: Float, y: Float) {
        touchDownView.visibility = VISIBLE

        touchDownFighterColumn = computeColumn(x)
        touchDownFighterRow = computeRow(y)
        val fighterViewId = fighterViewList[computeFighterIndex(touchDownFighterColumn, touchDownFighterRow)].id
        touchDownView.updateLayoutParams<LayoutParams> {
            startToStart = fighterViewId
            endToEnd = fighterViewId
            topToTop = fighterViewId
            bottomToBottom = fighterViewId
        }
    }

    private fun handleSwipe(x: Float, y: Float) {
        val targetColumn = computeColumn(x)
        val targetRow = computeRow(y)

        if (checkSameFighter(targetColumn, targetRow)) return

        if (checkVerticalLine(targetColumn, targetRow)) return
        if (checkHorizontalLine(targetRow, targetColumn)) return

        val firstFighterIndex = computeFighterIndex(touchDownFighterColumn, touchDownFighterRow)
        if (firstFighterIndex % 2 != 0) { //side fighter
            return
        }

        if (checkUpwardsLine(targetRow, targetColumn)) return
        if (checkDownwardsLine(targetRow, targetColumn)) return
    }

    private fun checkSameFighter(targetColumn: Int, targetRow: Int): Boolean {
        if (touchDownFighterColumn == targetColumn && touchDownFighterRow == targetRow) {
            cancelCurrentEvent()
            return true
        }
        return false
    }

    private fun checkDownwardsLine(targetRow: Int, targetColumn: Int): Boolean {
        if (((targetRow > touchDownFighterRow && targetColumn > touchDownFighterColumn)
                    || (targetRow < touchDownFighterRow && targetColumn < touchDownFighterColumn))
            && isDownwardsLineAvailable()
        ) {
            updateSelectingLine(downwardsView)
            updateCurrentLine(LineInfo(targetColumn, targetRow, LineType.DOWNWARDS))
            return true
        }
        return false
    }

    private fun checkUpwardsLine(targetRow: Int, targetColumn: Int): Boolean {
        if (((targetRow < touchDownFighterRow && targetColumn > touchDownFighterColumn)
                    || (targetRow > touchDownFighterRow && targetColumn < touchDownFighterColumn))
            && isUpwardsLineAvailable()
        ) {
            updateSelectingLine(upwardsView)
            updateCurrentLine(LineInfo(targetColumn, targetRow, LineType.UPWARDS))
            return true
        }
        return false
    }

    private fun getCurrentUpwardsResource() = when (currentLine) {
        L1 -> R.drawable.img_swipe_1_upwards
        L2 -> R.drawable.img_swipe_2_upwards
        L3 -> R.drawable.img_swipe_3_upwards
    }

    private fun getCurrentDownwardsResource() = when (currentLine) {
        L1 -> R.drawable.img_swipe_1_downwards
        L2 -> R.drawable.img_swipe_2_downwards
        L3 -> R.drawable.img_swipe_3_downwards
    }

    private fun isUpwardsLineAvailable(): Boolean = line1Info?.type != LineType.UPWARDS
            && line2Info?.type != LineType.UPWARDS
            && line3Info?.type != LineType.UPWARDS

    private fun isDownwardsLineAvailable(): Boolean = line1Info?.type != LineType.DOWNWARDS
            && line2Info?.type != LineType.DOWNWARDS
            && line3Info?.type != LineType.DOWNWARDS

    private fun checkHorizontalLine(targetRow: Int, targetColumn: Int): Boolean {
        if (targetRow == touchDownFighterRow
            && targetColumn != touchDownFighterColumn
            && isHorizontalLineAvailable(targetRow)
        ) {
            updateSelectingLine(getCurrentHorizontalView())

            val targetFighterId = fighterViewList[computeFighterIndex(targetColumn, targetRow)].id
            selectingLineView?.updateLayoutParams<LayoutParams> {
                startToStart = ConstraintSet.PARENT_ID
                endToEnd = ConstraintSet.PARENT_ID
                topToTop = targetFighterId
                bottomToBottom = targetFighterId
            }
            updateCurrentLine(LineInfo(targetColumn, targetRow, LineType.HORIZONTAL))
            return true
        }
        return false
    }

    private fun getCurrentHorizontalView() = when (currentLine) {
        L1 -> horizontal1View
        L2 -> horizontal2View
        L3 -> horizontal3View
    }

    private fun checkVerticalLine(targetColumn: Int, targetRow: Int): Boolean {
        if (targetColumn == touchDownFighterColumn
            && targetRow != touchDownFighterRow
            && isVerticalLineAvailable(targetColumn)
        ) {
            updateSelectingLine(getCurrentVerticalView())
            val targetFighterId = fighterViewList[computeFighterIndex(targetColumn, targetRow)].id
            selectingLineView?.updateLayoutParams<LayoutParams> {
                startToStart = targetFighterId
                endToEnd = targetFighterId
                topToTop = ConstraintSet.PARENT_ID
                bottomToBottom = ConstraintSet.PARENT_ID
            }
            updateCurrentLine(LineInfo(targetColumn, targetRow, LineType.VERTICAL))
            return true
        }
        return false
    }

    private fun getCurrentVerticalView() = when (currentLine) {
        L1 -> vertical1View
        L2 -> vertical2View
        L3 -> vertical3View
    }

    private fun updateSelectingLine(newView: View?) {
        selectingLineView?.visibility = GONE
        selectingLineView = newView
        selectingLineView?.visibility = VISIBLE
        selectingLineView?.elevation = when (currentLine) {
            L1 -> 1f
            L2 -> 2f
            L3 -> 3f
        }
    }

    private fun updateCurrentLine(lineInfo: LineInfo?) = when (currentLine) {
        L1 -> line1Info = lineInfo
        L2 -> line2Info = lineInfo
        L3 -> line3Info = lineInfo
    }

    private fun isVerticalLineAvailable(targetColumn: Int): Boolean {
        if (line1Info?.type == LineType.VERTICAL && line1Info!!.columnIndex == targetColumn) return false
        if (line2Info?.type == LineType.VERTICAL && line2Info!!.columnIndex == targetColumn) return false
        if (line3Info?.type == LineType.VERTICAL && line3Info!!.columnIndex == targetColumn) return false
        return true
    }

    private fun isHorizontalLineAvailable(targetRow: Int): Boolean {
        if (line1Info?.type == LineType.HORIZONTAL && line1Info!!.rowIndex == targetRow) return false
        if (line2Info?.type == LineType.HORIZONTAL && line2Info!!.rowIndex == targetRow) return false
        if (line3Info?.type == LineType.HORIZONTAL && line3Info!!.rowIndex == targetRow) return false
        return true
    }

    private fun validateLine() {
        if (canValidateLine()) {
            selectingLineView = null
            if (currentLine == L3) {
                allowSwipe = false
                listener?.onSwipeFinished(line1Info!!, line2Info!!, line3Info!!)
            }
            currentLine = currentLine.nextLine()
            updateResources()
            touchDownView.setBackgroundResource(
                when (currentLine) {
                    L1 -> R.drawable.img_swipe_1_click
                    L2 -> R.drawable.img_swipe_2_click
                    L3 -> R.drawable.img_swipe_3_click
                }
            )
        } else {
            cancelCurrentEvent()
        }
        touchDownView.visibility = GONE
    }

    private fun cancelCurrentEvent() {
        selectingLineView?.visibility = GONE
        updateCurrentLine(null)
    }

    private fun updateResources() {
        if (isUpwardsLineAvailable()) {
            upwardsView.setBackgroundResource(getCurrentUpwardsResource())
        }
        if (isDownwardsLineAvailable()) {
            downwardsView.setBackgroundResource(getCurrentDownwardsResource())
        }
    }

    private fun canValidateLine() = (currentLine == L1 && line1Info != null)
            || (currentLine == L2 && line2Info != null)
            || (currentLine == L3 && line3Info != null)

    private fun computeColumn(xPos: Float) = ((xPos / width) * 3).toInt()
    private fun computeRow(yPos: Float) = ((yPos / height) * 3).toInt()
    private fun computeFighterIndex(columnIndex: Int, rowIndex: Int) = min(columnIndex + 3 * rowIndex, 8)

}