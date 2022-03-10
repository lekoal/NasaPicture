package com.example.nasapicture.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import com.example.nasapicture.databinding.FragmentTextInfoBinding

class TextInfoFragment : Fragment() {

    private var _binding: FragmentTextInfoBinding? = null
    private val binding: FragmentTextInfoBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTextInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = TextInfoFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tfButtonUp.setOnClickListener {
            binding.tfAppBar.setExpanded(false)
        }

        binding.tfButtonDown.setOnClickListener {
            binding.tfAppBar.setExpanded(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}