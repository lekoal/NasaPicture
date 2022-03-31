package com.example.nasapicture.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nasapicture.R
import com.example.nasapicture.databinding.FragmentSplashScreenBinding
import com.example.nasapicture.tools.ViewBindingFragment

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : ViewBindingFragment<FragmentSplashScreenBinding>(FragmentSplashScreenBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        @JvmStatic
        fun newInstance() = SplashScreenFragment()
    }
}