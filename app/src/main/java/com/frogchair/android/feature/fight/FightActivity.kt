package com.frogchair.android.feature.fight

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.frogchair.android.R
import com.frogchair.android.common.utils.animateUpDown
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FightActivity : AppCompatActivity() {

    companion object {
        const val ANIM_ATTACK_DURATION = 300L

        fun navigate(context: Context) = Intent(context, FightActivity::class.java)
    }

    private val stepTextView: TextView by lazy { findViewById(R.id.fight_step_text) }

    private val enemy1View: View by lazy { findViewById(R.id.fight_enemy_1) }
    private val enemy2View: View by lazy { findViewById(R.id.fight_enemy_2) }
    private val enemy3View: View by lazy { findViewById(R.id.fight_enemy_3) }
    private val ally1View: View by lazy { findViewById(R.id.fight_ally_1) }
    private val ally2View: View by lazy { findViewById(R.id.fight_ally_2) }
    private val ally3View: View by lazy { findViewById(R.id.fight_ally_3) }

    private val swipeView: SwipeView by lazy { findViewById(R.id.fight_swipe_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fight)

        initSwipeView()
        animateEnemies()
    }

    private fun animateEnemies() {
        enemy1View.animateUpDown()
        enemy2View.animateUpDown()
        enemy3View.animateUpDown()
    }

    private fun initSwipeView() {
        swipeView.listener = object : SwipeView.Listener {
            override fun onSwipeFinished(lineInfo1: LineInfo, lineInfo2: LineInfo, lineInfo3: LineInfo) {
                animateFirstLine()
            }
        }
    }

    private fun animateFirstLine() {
        ally1View.visibility = View.VISIBLE
        ally2View.visibility = View.VISIBLE
        ally3View.visibility = View.VISIBLE

        val enterAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.anim_translate_left_right)
        enterAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) = onEnterAnimationEnd()
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
        })
        ally1View.startAnimation(enterAnim)
        ally2View.startAnimation(enterAnim)
        ally3View.startAnimation(enterAnim)
    }

    private fun onEnterAnimationEnd() {
        ally2View.animateUpDown()
        ally3View.animateUpDown()
        animateAllyAttack(ally1View, enemy3View) {
            ally1View.animateUpDown()
            animateAlly2Attack()
        }
    }

    private fun animateAlly2Attack() {
        animateAllyAttack(ally2View, enemy1View) {
            ally2View.animateUpDown()
            animateAlly3Attack()
        }
    }

    private fun animateAlly3Attack() {
        animateAllyAttack(ally3View, enemy2View) {
            ally3View.animateUpDown()
            stepTextView.text = "Wow such strenght"
            closeScreen()
        }
    }

    private fun closeScreen() {
        GlobalScope.launch {
            delay(1000)
            finish()
        }
    }

    private fun animateAllyAttack(allyView: View, enemyView: View, onAnimationEnd: () -> Unit) {
        allyView.animation?.cancel()
        val anim = TranslateAnimation(
            0f, enemyView.x - allyView.x - enemyView.width / 2,
            0f, enemyView.y - allyView.y
        ).apply {
            duration = ANIM_ATTACK_DURATION
            repeatCount = 1
            repeatMode = Animation.REVERSE
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) = onAnimationEnd()
                override fun onAnimationRepeat(animation: Animation?) {
                    enemyView.visibility = View.GONE //dead
                }
            })
        }
        allyView.startAnimation(anim)
    }

}