package com.example.circularrevealanimationsample

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.circularrevealanimationsample.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.hypot
import kotlin.math.max

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var revealLayout: View
    private lateinit var floatingActionButton: FloatingActionButton
    private var isRevealed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        revealLayout = binding.revealLayout
        floatingActionButton = binding.floatingActionButton

        floatingActionButton.setOnClickListener {
            if (isRevealed) {
                hideReveal()
            } else {
                showReveal()
            }
        }

        revealLayout.setOnClickListener {
            hideReveal()
        }
    }

    private fun showReveal() {
        val x: Int = revealLayout.width / 2
        val y: Int = revealLayout.height / 2

        // here the starting radius of the reveal layout is 0 when it is not visible
        val startRadius = 0

        // make the end radius should match the while parent view
        val endRadius = hypot(
            revealLayout.width.toDouble(),
            revealLayout.height.toDouble()
        ).toInt()

        // create the instance of the ViewAnimationUtils to initiate the circular reveal animation
        val anim = ViewAnimationUtils.createCircularReveal(
            revealLayout,
            x,
            y,
            startRadius.toFloat(),
            endRadius.toFloat()
        )

        // make the invisible reveal layout to visible so that upon revealing it can be visible to user
        revealLayout.visibility = View.VISIBLE
        anim.start()
        isRevealed = true
    }

    private fun hideReveal() {
        val x: Int = revealLayout.width / 2
        val y: Int = revealLayout.height / 2

        // here the starting radius of the reveal layout is its full width
        val startRadius: Int = max(revealLayout.width, revealLayout.height)

        // and the end radius should be zero at this point because the layout should be closed
        val endRadius = 0

        // create the instance of the ViewAnimationUtils to initiate the circular reveal animation
        val anim = ViewAnimationUtils.createCircularReveal(
            revealLayout,
            x,
            y,
            startRadius.toFloat(),
            endRadius.toFloat()
        )

        // now as soon as the animation is ending, the reveal layout should also be closed
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                revealLayout.visibility = View.INVISIBLE
            }
        })
        anim.start()
        isRevealed = false
    }
}

