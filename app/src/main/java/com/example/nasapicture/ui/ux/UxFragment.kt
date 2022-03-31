package com.example.nasapicture.ui.ux

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
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
                   navigateTo(UxTextFragment())
                }
                R.id.ux_button_fragment -> {
                   navigateTo(UxButtonFragment())
                }
            }
            true
        }
        binding.uxBottomNavigationView.selectedItemId = R.id.ux_text_fragment
    }

    private fun navigateTo(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            ?.replace(R.id.ux_main, fragment)
            ?.commit()
    }
}