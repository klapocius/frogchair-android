package com.frogchair.android.feature.summon.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.frogchair.android.R
import com.frogchair.android.common.utils.onClick
import com.frogchair.android.common.utils.showToast
import com.frogchair.android.feature.home.ui.HomeActivity
import com.frogchair.android.feature.summon.details.SummonDetailsActivity

class SummonHomeActivity : AppCompatActivity() {

    companion object {
        fun navigate(context: Context) = Intent(context, SummonHomeActivity::class.java)
    }

    private val toolbarText: TextView by lazy { findViewById(R.id.title_toolbar_text) }
    private val toolbarHomeButton: View by lazy { findViewById(R.id.title_home_button) }
    private val backArrow: View by lazy { findViewById(R.id.summon_home_back_arrow) }
    private val bubbleView: TextView by lazy { findViewById(R.id.summon_home_bubble) }

    private val boxSample1: View by lazy { findViewById(R.id.summon_home_box_sample_1) }
    private val boxSample2: View by lazy { findViewById(R.id.summon_home_box_sample_2) }
    private val boxSample3: View by lazy { findViewById(R.id.summon_home_box_sample_3) }
    private val boxSample4: View by lazy { findViewById(R.id.summon_home_box_sample_4) }
    private val boxSample5: View by lazy { findViewById(R.id.summon_home_box_sample_5) }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summon_home)

        backArrow.onClick { onBackPressed() }

        toolbarText.text = "BUILD"
        toolbarHomeButton.onClick { startActivity(HomeActivity.navigate(this)) }

        bubbleView.onClick { onBubbleViewClick() }

        boxSample1.setOnTouchListener { v, event -> handleBoxEvent(boxSample1, event) }
        boxSample2.setOnTouchListener { v, event -> handleBoxEvent(boxSample2, event) }
        boxSample3.setOnTouchListener { v, event -> handleBoxEvent(boxSample3, event) }
        boxSample4.setOnTouchListener { v, event -> handleBoxEvent(boxSample4, event) }
        boxSample5.setOnTouchListener { v, event -> handleBoxEvent(boxSample5, event) }


        boxSample1.onClick { goToSummonDetails() }
        boxSample2.onClick { goToSummonDetails() }
        boxSample3.onClick { goToSummonDetails() }
        boxSample4.onClick { goToSummonDetails() }
        boxSample5.onClick { goToSummonDetails() }

    }

    private fun goToSummonDetails() {
        startActivity(SummonDetailsActivity.navigate(this))
    }

    var bubbleClickCount = 0
    private fun onBubbleViewClick() {
        if (bubbleClickCount == 10) {
            showToast("TO THE MOOON")
            bubbleClickCount = 0
        }
        ++bubbleClickCount
    }

    private fun handleBoxEvent(boxSample: View, event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            boxSample.translationY = 15f
        }
        if (event?.action == MotionEvent.ACTION_UP) {
            boxSample.translationY = 0f
        }
        return false
    }

}