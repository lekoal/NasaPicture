package com.example.nasapicture.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Property
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nasapicture.databinding.FragmentAnimationBinding

class AnimationFragment : Fragment() {

    private var _binding: FragmentAnimationBinding? = null
    private val binding get() = _binding!!

    private var flag = false

    private val duration = 2000L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            flag = !flag
            if (flag) {
                objectAnimatorStart(binding.fab, View.ROTATION, 0f, 405f)
                objectAnimatorStart(binding.optionOneContainer, View.TRANSLATION_Y, 0f, -250f)
                objectAnimatorStart(binding.optionOneContainer, View.SCALE_X, 0f, 1f)
                objectAnimatorStart(binding.optionTwoContainer, View.TRANSLATION_Y, 0f, -160f)
                objectAnimatorStart(binding.optionTwoContainer, View.SCALE_X, 0f, 1f)

                binding.transparentScreen.animate()
                    .alpha(0.6f)
                    .setDuration(duration)
                    .start()

                binding.optionOneContainer.animate()
                    .alpha(1f)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            binding.optionOneContainer.isClickable = true
                        }
                    })

                binding.optionTwoContainer.animate()
                    .alpha(1f)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            binding.optionTwoContainer.isClickable = true
                        }
                    })
            } else {
                objectAnimatorStart(binding.fab, View.ROTATION, 405f, 0f)
                objectAnimatorStart(binding.optionOneContainer, View.TRANSLATION_Y, -250f, 0f)
                objectAnimatorStart(binding.optionOneContainer, View.SCALE_X, 1f, 0f)
                objectAnimatorStart(binding.optionTwoContainer, View.TRANSLATION_Y, -160f, 0f)
                objectAnimatorStart(binding.optionTwoContainer, View.SCALE_X, 1f, 0f)

                binding.transparentScreen.animate()
                    .alpha(0f)
                    .setDuration(duration)
                    .start()

                binding.optionOneContainer.animate()
                    .alpha(0f)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            binding.optionOneContainer.isClickable = false
                        }
                    })

                binding.optionTwoContainer.animate()
                    .alpha(0f)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            binding.optionTwoContainer.isClickable = false
                        }
                    })
            }
        }
    }

    private fun objectAnimatorStart(view: View, property: Property<View, Float>, start: Float, end: Float) {
        ObjectAnimator.ofFloat(view, property, start, end).setDuration(duration).start()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}