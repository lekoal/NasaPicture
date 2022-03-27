package com.example.nasapicture.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.example.nasapicture.R
import com.example.nasapicture.databinding.FragmentAnimationBonusStartBinding

class AnimationBonusStartFragment : Fragment() {

    private var _binding: FragmentAnimationBonusStartBinding? = null
    private val binding get() = _binding!!

    private var flag = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationBonusStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.constraintContainer)

        binding.backgroundImage.setOnClickListener {
            flag = !flag
            val changeBounds = ChangeBounds()
            changeBounds.duration = 1000
            changeBounds.interpolator = AnticipateOvershootInterpolator(1.5f)
            TransitionManager.beginDelayedTransition(binding.constraintContainer, changeBounds)
            if (flag) {
                constraintSet.connect(R.id.title, ConstraintSet.END, R.id.constraint_container, ConstraintSet.END)
                constraintSet.clear(R.id.description, ConstraintSet.TOP)
                constraintSet.connect(
                    R.id.description,
                    ConstraintSet.BOTTOM,
                    R.id.background_image,
                    ConstraintSet.BOTTOM
                )
                constraintSet.applyTo(binding.constraintContainer)
            } else {
                constraintSet.connect(R.id.title, ConstraintSet.END, R.id.constraint_container, ConstraintSet.START)
                constraintSet.clear(R.id.description, ConstraintSet.BOTTOM)
                constraintSet.connect(
                    R.id.description,
                    ConstraintSet.TOP,
                    R.id.background_image,
                    ConstraintSet.BOTTOM
                )
                constraintSet.applyTo(binding.constraintContainer)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AnimationBonusStartFragment()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}