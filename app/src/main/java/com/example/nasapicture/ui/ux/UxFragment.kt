package com.example.nasapicture.ui.ux

import android.os.Bundle
import android.view.View
import com.example.nasapicture.R
import com.example.nasapicture.databinding.FragmentUxBinding
import com.example.nasapicture.tools.ViewBindingFragment

class UxFragment : ViewBindingFragment<FragmentUxBinding>(FragmentUxBinding::inflate) {

    companion object {
        @JvmStatic
        fun newInstance() = UxFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.uxBottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.ux_text_fragment -> {
                    binding.uxMainText.visibility = View.GONE
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.slide_out
                        )
                        ?.replace(R.id.ux_main, UxTextFragment())
                        ?.addToBackStack("")
                        ?.commit()
                }
                R.id.ux_button_fragment -> {
                    binding.uxMainText.visibility = View.GONE
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.slide_out
                        )
                        ?.replace(R.id.ux_main, UxButtonFragment())
                        ?.addToBackStack("")
                        ?.commit()
                }
            }
            true
        }
    }
}