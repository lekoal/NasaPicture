package com.example.nasapicture.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.nasapicture.R
import com.example.nasapicture.databinding.NavigationDrawerLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

const val SAVED_THEME_FILE = "SAVED THEME"
const val CURRENT_THEME = "CURRENT THEME"

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: NavigationDrawerLayoutBinding? = null
    private val binding get(): NavigationDrawerLayoutBinding = _binding!!

    private val saveTheme = context?.getSharedPreferences(SAVED_THEME_FILE, Context.MODE_PRIVATE)
    private val editor = saveTheme?.edit()

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
                R.id.base_theme -> {
                    editor?.putString(CURRENT_THEME, "BaseTheme")
                    editor?.apply()
                }
                R.id.red_theme -> {
                    editor?.putString(CURRENT_THEME, "RedTheme")
                    editor?.apply()
                }
                R.id.green_theme -> {
                    editor?.putString(CURRENT_THEME, "GreenTheme")
                    editor?.apply()
                }
                R.id.blue_theme -> {
                    editor?.putString(CURRENT_THEME, "BlueTheme")
                    editor?.apply()
                }
                R.id.night_theme -> {
                    editor?.putString(CURRENT_THEME, "NightTheme")
                    editor?.apply()
                }
            }
            true
        }
    }
}