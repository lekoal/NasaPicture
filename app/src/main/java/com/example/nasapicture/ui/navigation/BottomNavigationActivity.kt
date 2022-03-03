package com.example.nasapicture.ui.navigation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.nasapicture.R
import com.example.nasapicture.databinding.ActivityBottomNavigationBinding
import com.example.nasapicture.ui.CURRENT_THEME
import com.example.nasapicture.ui.SAVED_THEME_FILE
import com.example.nasapicture.viewmodel.PictureOfTheDayState
import com.google.android.material.snackbar.Snackbar

class BottomNavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectTheme()
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nasa_earth_image -> {
                    navigationTo(EarthFragment())
                    true
                }
                R.id.nasa_mars_image -> {
                    navigationTo(MarsFragment())
                    true
                }
                R.id.nasa_solar_system_image -> {
                    navigationTo(SolarSystemFragment())
                    true
                }
                else -> true
            }
        }
        binding.bottomNavigationView.selectedItemId = R.id.nasa_earth_image
    }

    private fun selectTheme() {
        val saveTheme = this.getSharedPreferences(SAVED_THEME_FILE, Context.MODE_PRIVATE)
        when (saveTheme.getInt(CURRENT_THEME, -1)) {
            R.style.BaseTheme -> {
                setTheme(R.style.BaseTheme)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            R.style.RedTheme -> {
                setTheme(R.style.RedTheme)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            R.style.GreenTheme -> {
                setTheme(R.style.GreenTheme)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            R.style.BlueTheme -> {
                setTheme(R.style.BlueTheme)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            0 -> {
                setTheme(R.style.BaseTheme)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else -> {
                setTheme(R.style.BaseTheme)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun navigationTo(f: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, f).commit()
    }
}