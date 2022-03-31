package com.example.nasapicture.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.nasapicture.R
import com.example.nasapicture.databinding.FragmentSplashScreenBinding
import com.example.nasapicture.tools.ViewBindingFragment

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment :
    ViewBindingFragment<FragmentSplashScreenBinding>(FragmentSplashScreenBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.splashImage.animate().rotationBy(720f).setDuration(4000L).start()

        Handler(Looper.getMainLooper()).postDelayed({
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                .replace(R.id.container, PictureOfTheDayFragment())
                .commit()
        }, 3000L)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SplashScreenFragment()
    }
}