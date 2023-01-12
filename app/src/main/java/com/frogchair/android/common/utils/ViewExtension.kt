package com.frogchair.android.common.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.animation.addListener
import androidx.core.content.ContextCompat
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SafeClickListener(
    private val interval: Int = 300,
    private val onSafeClick: (View) -> Unit
) : View.OnClickListener {

    private var lastClickTime: Long = 0L

    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastClickTime < interval) {
            return
        }
        lastClickTime = SystemClock.elapsedRealtime()
        onSafeClick(v)
    }
}

fun View.onClick(
    onSafeClick: (View) -> Unit
) {
    setOnClickListener(SafeClickListener { v ->
        onSafeClick(v)
    })
}

fun View.animateUpDown() {
    val yOffset = -10f
    val wait = 270L
    val movement = 40L
    val property = "translationY"

    val anim = ObjectAnimator.ofFloat(this, property, 0f, yOffset)
    anim.duration = movement
    val anim2 = ObjectAnimator.ofFloat(this, property, yOffset, yOffset)
    anim2.duration = wait
    val anim3 = ObjectAnimator.ofFloat(this, property, yOffset, 0f)
    anim3.duration = movement
    val anim4 = ObjectAnimator.ofFloat(this, property, 0f, 0f)
    anim4.duration = wait

    AnimatorSet().apply {
        playSequentially(anim, anim2, anim3, anim4)
        duration
        addListener(onEnd = { start() })
        MainScope().launch {
            delay((0..200L).random())
            start()
        }
    }

}

fun ImageView.setDrawableAliasing(@DrawableRes resId: Int) {
    val drawable = ContextCompat.getDrawable(context, resId)
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        setImageDrawable(AliasingDrawableWrapper(drawable))
    } else {
        setImageDrawable(drawable)
    }
}
