package com.example.nasapicture.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.nasapicture.R

const val SAVED_THEME_FILE = "SavedTheme"
const val CURRENT_THEME = "CURRENT_THEME"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectTheme()
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.container,
                PictureOfTheDayFragment.newInstance()
            ).commit()
        }
    }

    fun selectTheme() {
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
}