package com.example.nasapicture.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.nasapicture.R
import com.example.nasapicture.databinding.NavigationDrawerLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: NavigationDrawerLayoutBinding? = null
    private val binding get(): NavigationDrawerLayoutBinding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NavigationDrawerLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.navigation_one -> Toast.makeText(context, "Экран 1", Toast.LENGTH_SHORT).show()
                R.id.navigation_two -> Toast.makeText(context, "Экран 2", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }
}