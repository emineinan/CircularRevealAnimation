package com.example.circularrevealanimationsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.animation.Animator
import android.content.res.ColorStateList
import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.content.res.ResourcesCompat
import com.example.circularrevealanimationsample.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.hypot
import kotlin.math.max

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var revealLayout: View
    private lateinit var floatingActionButton: FloatingActionButton

    // boolean variable to check whether the
    // reveal layout is visible or not
    private var isRevealed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        revealLayout = binding.revealLayout
        floatingActionButton = binding.floatingActionButton

        // initially the color of the FAB should be green
        floatingActionButton.backgroundTintList = ColorStateList.valueOf(
            ResourcesCompat.getColor(
                resources,
                R.color.blue,
                null
            )
        )

        // upon clicking the FAB the reveal should be
        // toggled according to the boolean value
        floatingActionButton.setOnClickListener {
            showReveal()
        }
    }

    // this function is triggered when
    // the FAB is clicked
    private fun showReveal() {

        // based on the boolean value the
        // reveal layout should be toggled
        if (!isRevealed) {

            // get the right and bottom side
            // lengths of the reveal layout
            val x: Int = revealLayout.width / 2
            val y: Int = revealLayout.height / 2

            // here the starting radius of the reveal
            // layout is 0 when it is not visible
            val startRadius = 0

            // make the end radius should match
            // the while parent view
            val endRadius = hypot(
                revealLayout.width.toDouble(),
                revealLayout.height.toDouble()
            ).toInt()

            // and set the background tint of the FAB to white
            // color so that it can be visible
            floatingActionButton.backgroundTintList = ColorStateList.valueOf(
                ResourcesCompat.getColor(
                    resources,
                    R.color.white,
                    null
                )
            )
            // now set the icon as close for the FAB
            floatingActionButton.setImageResource(R.drawable.ic_close)

            // create the instance of the ViewAnimationUtils to
            // initiate the circular reveal animation
            val anim = ViewAnimationUtils.createCircularReveal(
                revealLayout,
                x,
                y,
                startRadius.toFloat(),
                endRadius.toFloat()
            )

            // make the invisible reveal layout to visible
            // so that upon revealing it can be visible to user
            revealLayout.visibility = View.VISIBLE
            // now start the reveal animation
            anim.start()

            // set the boolean value to true as the reveal
            // layout is visible to the user
            isRevealed = true

        } else {

            // get the right and bottom side lengths
            // of the reveal layout
            val x: Int = revealLayout.width / 2
            val y: Int = revealLayout.height / 2

            // here the starting radius of the reveal layout is its full width
            val startRadius: Int = max(revealLayout.width, revealLayout.height)

            // and the end radius should be zero
            // at this point because the layout should be closed
            val endRadius = 0

            // now set the background tint of the FAB to green
            // so that it can be visible to the user
            floatingActionButton.backgroundTintList = ColorStateList.valueOf(
                ResourcesCompat.getColor(
                    resources,
                    R.color.red,
                    null
                )
            )

            // now again set the icon of the FAB to plus
            floatingActionButton.setImageResource(R.drawable.ic_add)

            // create the instance of the ViewAnimationUtils to
            // initiate the circular reveal animation
            val anim = ViewAnimationUtils.createCircularReveal(
                revealLayout,
                x,
                y,
                startRadius.toFloat(),
                endRadius.toFloat()
            )

            // now as soon as the animation is ending, the reveal
            // layout should also be closed
            anim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {}
                override fun onAnimationEnd(animator: Animator) {
                    revealLayout.visibility = View.GONE
                }

                override fun onAnimationCancel(animator: Animator) {}
                override fun onAnimationRepeat(animator: Animator) {}
            })

            // start the closing animation
            anim.start()

            // set the boolean variable to false
            // as the reveal layout is invisible
            isRevealed = false
        }
    }
}

