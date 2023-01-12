package com.frogchair.android.feature.mission

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.frogchair.android.R
import com.frogchair.android.common.ui.PrimaryButton
import com.frogchair.android.common.utils.animateUpDown
import com.frogchair.android.common.utils.onClick
import com.frogchair.android.feature.fight.FightActivity
import com.frogchair.android.feature.home.ui.HomeActivity
import kotlin.random.Random


class MissionActivity : AppCompatActivity() {

    companion object {
        fun navigate(context: Context) = Intent(context, MissionActivity::class.java)
    }

    private val mainLayout: ConstraintLayout by lazy { findViewById(R.id.mission_constraint_layout) }
    private val backgroundMiddleFirst: View by lazy { findViewById(R.id.mission_background_middle_1) }
    private val backgroundMiddleSecond: View by lazy { findViewById(R.id.mission_background_middle_2) }

    private val mainFighter: ImageView by lazy { findViewById(R.id.mission_main_fighter) }
    private val homeButton: ImageView by lazy { findViewById(R.id.toolbar_home_view) }
    private val backArrow: View by lazy { findViewById(R.id.mission_navigation_back_arrow) }
    private val progressBar: ProgressBar by lazy { findViewById(R.id.mission_progress_bar) }
    private val goButton: PrimaryButton by lazy { findViewById(R.id.mission_navigation_main) }

    private var moving = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission)

        homeButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.selector_home))
        homeButton.onClick { goToHome() }
        backArrow.onClick { goToHome() }

        progressBar.progress = 0
        progressBar.max = 20

        goButton.displayGo()
        goButton.setOnClickListener { onGoClicked() }

        mainFighter.animateUpDown()
    }

    private fun onGoClicked() {
        if (moving) return
        moving = true

        progressBar.progress += 1

        when (progressBar.progress) {
            20 -> "Congratz !"
            25 -> "Alright you already won"
            30 -> "Game broken plz stop"
            35 -> "eqklfk,eklsq,s"
            else -> null
        }?.let {
            progressBar.max = 40
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        moveProgressbarThumb(progressBar.progress.toFloat() / progressBar.max)
        translateBackground()
    }

    private fun translateBackground() {
        val anim: Animation = TranslateAnimation(0f, -backgroundMiddleFirst.width.toFloat(), 0f, 0f)
        anim.duration = 800
        anim.fillAfter = true
        anim.interpolator = AccelerateDecelerateInterpolator()
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                moving = false
                onBackgroundAnimEnd()
            }
        })
        backgroundMiddleFirst.startAnimation(anim)
        backgroundMiddleSecond.startAnimation(anim)
    }

    private fun onBackgroundAnimEnd() {
        if (Random.nextInt(3) == 0) {
            startActivity(FightActivity.navigate(this))
        }
    }

    private fun moveProgressbarThumb(progressPercent: Float) {
        ConstraintSet().apply {
            clone(mainLayout)
            setHorizontalBias(R.id.mission_progress_thumb, progressPercent)
            applyTo(mainLayout)
        }
    }

    override fun onBackPressed() {
        goToHome()
    }

    private fun goToHome() {
        startActivity(HomeActivity.navigate(this))
        finish()
    }

}