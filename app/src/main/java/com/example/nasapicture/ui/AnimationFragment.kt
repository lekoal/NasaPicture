package com.example.nasapicture.ui

import android.animation.ObjectAnimator
import android.os.Bundle
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
                ObjectAnimator.ofFloat(binding.fab, View.ROTATION, 0f, 405f).setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, 0f, -250f).setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionOneContainer, View.SCALE_X, 0f, 1f).setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, 0f, -160f).setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionTwoContainer, View.SCALE_X, 0f, 1f).setDuration(duration).start()
            } else {
                ObjectAnimator.ofFloat(binding.fab, View.ROTATION, 405f, 0f).setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, -250f, 0f).setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionOneContainer, View.SCALE_X, 1f, 0f).setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, -160f, 0f).setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionTwoContainer, View.SCALE_X, 1f, 0f).setDuration(duration).start()

            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}