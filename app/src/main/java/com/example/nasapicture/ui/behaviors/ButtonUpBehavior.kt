package com.example.nasapicture.ui.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class ButtonUpBehavior(context: Context, attr: AttributeSet) :
    CoordinatorLayout.Behavior<View>(context, attr) {
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        val bar = dependency as AppBarLayout
        val barHeight = bar.height.toFloat()
        val barY = bar.y
        if (abs(barY) > barHeight * 0.6) {
            child.visibility = View.GONE
        } else {
            child.visibility = View.VISIBLE
            child.alpha = ((barHeight * 0.6 - abs(barY))/barHeight).toFloat()
        }

        return super.onDependentViewChanged(parent, child, dependency)
    }
}