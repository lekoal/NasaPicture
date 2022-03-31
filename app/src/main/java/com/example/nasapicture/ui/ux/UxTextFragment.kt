package com.example.nasapicture.ui.ux

import com.example.nasapicture.databinding.FragmentUxTextBinding
import com.example.nasapicture.tools.ViewBindingFragment

class UxTextFragment : ViewBindingFragment<FragmentUxTextBinding>(FragmentUxTextBinding::inflate) {

    companion object {
        @JvmStatic
        fun newInstance() = UxTextFragment()
    }


}