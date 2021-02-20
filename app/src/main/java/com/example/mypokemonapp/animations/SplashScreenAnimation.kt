package com.example.mypokemonapp.animations

import android.animation.ObjectAnimator
import android.util.Property
import android.view.View
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

class SplashScreenAnimation {
    fun startAnimation(
        view: View,
        type: Property<View, Float>,
        value: Float,
        delay: Long = 1000
    ): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, type, value).apply {
            startDelay = delay
            duration = 500L
            interpolator = FastOutSlowInInterpolator()
        }
    }
}