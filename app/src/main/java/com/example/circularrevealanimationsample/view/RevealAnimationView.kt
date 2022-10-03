package com.example.circularrevealanimationsample.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.LinearLayout
import com.example.circularrevealanimationsample.databinding.RevealAnimationViewBinding
import kotlin.math.hypot
import kotlin.math.max

class RevealAnimationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding =
        RevealAnimationViewBinding.inflate(LayoutInflater.from(context), this, true)
    var isRevealed = false

    fun showRevealWithDescription(text: String) {
        setText(text)
        if (isRevealed) {
            hideReveal()
        } else {
            showReveal()
        }

        binding.revealLayout.setOnClickListener {
            if (isRevealed) {
                hideReveal()
            }
        }
    }

    private fun setText(text: String) {
        binding.textViewDescription.text = text
    }

    private fun showReveal() {
        val x: Int = binding.revealLayout.width / 2
        val y: Int = binding.revealLayout.height / 2

        // here the starting radius of the reveal layout is 0 when it is not visible
        val startRadius = 0

        // make the end radius should match the while parent view
        val endRadius = hypot(
            binding.revealLayout.width.toDouble(),
            binding.revealLayout.height.toDouble()
        ).toInt()

        // create the instance of the ViewAnimationUtils to initiate the circular reveal animation
        val anim = ViewAnimationUtils.createCircularReveal(
            binding.revealLayout,
            x,
            y,
            startRadius.toFloat(),
            endRadius.toFloat()
        )

        // make the invisible reveal layout to visible so that upon revealing it can be visible to user
        binding.revealLayout.visibility = View.VISIBLE
        anim.start()
        isRevealed = true
    }

    private fun hideReveal() {
        val x: Int = binding.revealLayout.width / 2
        val y: Int = binding.revealLayout.height / 2

        // here the starting radius of the reveal layout is its full width
        val startRadius: Int = max(binding.revealLayout.width, binding.revealLayout.height)

        // and the end radius should be zero at this point because the layout should be closed
        val endRadius = 0

        // create the instance of the ViewAnimationUtils to initiate the circular reveal animation
        val anim = ViewAnimationUtils.createCircularReveal(
            binding.revealLayout,
            x,
            y,
            startRadius.toFloat(),
            endRadius.toFloat()
        )

        // now as soon as the animation is ending, the reveal layout should also be closed
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                binding.revealLayout.visibility = View.INVISIBLE
            }
        })
        anim.start()
        isRevealed = false
    }
}