package com.mobvoi.demo.circular.reveal

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MotionEvent
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import kotlin.properties.Delegates

class AnimationActivity : AppCompatActivity() {
    private lateinit var root: View
    private var positionX: Int by Delegates.notNull()
    private var positionY: Int by Delegates.notNull()

    private val animatorListener = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            root.visibility = View.INVISIBLE
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)

        positionX = intent.getIntExtra("x", 0)
        positionY = intent.getIntExtra("y", 0)

        root = findViewById(R.id.root)
        root.post {
            if (positionX != 0 && positionY != 0) {
                createRevealAnimator(false, positionX, positionY).start()
            }
        }
        root.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (positionX != 0 && positionY != 0) {
                    createRevealAnimator(true, event.x.toInt(), event.y.toInt()).start()
                } else {
                    finish()
                }
            }
            true
        }
    }

    override fun onBackPressed() {
        if (positionX != 0 && positionY != 0) {
            createRevealAnimator(true, positionX, positionY).start()
        } else {
            finish()
        }
    }

    private fun createRevealAnimator(reversed: Boolean, x: Int, y: Int): Animator {
        val hypot = Math.hypot(root.width.toDouble(), root.height.toDouble()).toFloat()
        val startRadius = if (reversed) hypot else 0f
        val endRadius = if (reversed) 0f else hypot

        val animator = ViewAnimationUtils.createCircularReveal(root, x, y, startRadius, endRadius)
        animator.duration = 600
        animator.interpolator = AccelerateDecelerateInterpolator()
        if (reversed) {
            animator.addListener(animatorListener)
        }
        return animator
    }
}