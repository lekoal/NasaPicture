package com.example.nasapicture.ui.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nasapicture.databinding.FragmentSolarSystemBinding

class SolarSystemFragment : Fragment() {

    private var _binding: FragmentSolarSystemBinding? = null
    private val binding get(): FragmentSolarSystemBinding = _binding!!

    private val nasaId = "PIA02973"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSolarSystemBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SolarSystemFragment()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}