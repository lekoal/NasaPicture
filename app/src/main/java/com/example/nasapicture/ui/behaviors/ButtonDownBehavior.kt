package com.example.nasapicture.ui.behaviors

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class ButtonDownBehavior(context: Context, attr: AttributeSet) :
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
        child.elevation = 20F
        val bar = dependency as AppBarLayout
        val barHeight = bar.height.toFloat()
        val barY = bar.y
        if (abs(barY) < barHeight * 0.3) {
            child.visibility = View.GONE
        } else {
            child.visibility = View.VISIBLE
            child.alpha = ((abs(barY) - barHeight * 0.3) / (barHeight * 0.6)).toFloat()
        }

        return super.onDependentViewChanged(parent, child, dependency)
    }
}