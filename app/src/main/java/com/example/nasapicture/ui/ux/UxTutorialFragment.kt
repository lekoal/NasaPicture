package com.example.nasapicture.ui.ux

import android.os.Bundle
import android.view.View
import com.example.nasapicture.databinding.FragmentUxButtonBinding
import com.example.nasapicture.tools.ViewBindingFragment
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener

class UxTutorialFragment :
    ViewBindingFragment<FragmentUxButtonBinding>(FragmentUxButtonBinding::inflate) {

    companion object {
        @JvmStatic
        fun newInstance() = UxButtonFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GuideView.Builder(requireContext())
            .setTitle("Устаревший подход к дизайну кнопок")
            .setContentText("Работа с цветом, размером, шрифтами не осуществляется")
            .setGravity(Gravity.center)
            .setDismissType(DismissType.anywhere)
            .setTargetView(binding.wrongBlock)
            .setGuideListener {
                GuideView.Builder(requireContext())
                    .setTitle("Material подход к дизайну кнопок")
                    .setContentText("Работа осуществляется с прозрачностью и пространством")
                    .setGravity(Gravity.center)
                    .setDismissType(DismissType.anywhere)
                    .setTargetView(binding.correctBlock)
                    .build()
                    .show()
            }
            .build()
            .show()
    }
}