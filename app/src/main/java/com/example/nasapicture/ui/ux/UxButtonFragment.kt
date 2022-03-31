package com.example.nasapicture.ui.ux

import com.example.nasapicture.databinding.FragmentUxButtonBinding
import com.example.nasapicture.tools.ViewBindingFragment

class UxButtonFragment : ViewBindingFragment<FragmentUxButtonBinding>(FragmentUxButtonBinding::inflate) {

    companion object {
        @JvmStatic
        fun newInstance() = UxButtonFragment()
    }


}