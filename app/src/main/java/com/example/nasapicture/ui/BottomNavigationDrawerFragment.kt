package com.example.nasapicture.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.example.nasapicture.R
import com.example.nasapicture.databinding.NavigationDrawerLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: NavigationDrawerLayoutBinding? = null
    private val binding get(): NavigationDrawerLayoutBinding = _binding!!

    private lateinit var parentActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = requireActivity() as MainActivity

    }

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
                    saveThemeToFile(R.style.BaseTheme)
                    checkNightTheme()
                    parentActivity.selectTheme()
                    parentActivity.recreate()
                }
                R.id.red_theme -> {
                    saveThemeToFile(R.style.RedTheme)
                    checkNightTheme()
                    parentActivity.selectTheme()
                    parentActivity.recreate()
                }
                R.id.green_theme -> {
                    saveThemeToFile(R.style.GreenTheme)
                    checkNightTheme()
                    parentActivity.selectTheme()
                    parentActivity.recreate()
                }
                R.id.blue_theme -> {
                    saveThemeToFile(R.style.BlueTheme)
                    checkNightTheme()
                    parentActivity.selectTheme()
                    parentActivity.recreate()
                }
                R.id.night_theme -> {
                    checkNightTheme(true)
                }
            }
            this.dismiss()
            true
        }
    }
    private fun checkNightTheme(isNight: Boolean = false) {
        if (isNight && AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            saveThemeToFile(R.style.BaseTheme)
            parentActivity.recreate()
        } else if (!isNight && AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else if (isNight && AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            saveThemeToFile(0)
            parentActivity.recreate()
        }
    }

    private fun saveThemeToFile(themeId: Int) {
        val saveTheme = requireContext().getSharedPreferences(SAVED_THEME_FILE, Context.MODE_PRIVATE)
        val editor = saveTheme.edit()
        editor.putInt(CURRENT_THEME, themeId)
        editor.apply()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}