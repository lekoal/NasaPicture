package com.example.nasapicture.ui.navigation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewpager.widget.ViewPager
import com.example.nasapicture.R
import com.example.nasapicture.databinding.ActivityBottomNavigationBinding
import com.example.nasapicture.repository.ZoomOutPageTransformer
import com.example.nasapicture.ui.*

class BottomNavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavigationBinding

    private lateinit var pager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectTheme()
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pager = binding.viewPager

        pager.adapter = ViewPagerAdapter(supportFragmentManager)

        pager.setPageTransformer(true, ZoomOutPageTransformer())

        initBottomNavigationView()

        binding.tabLayout.setupWithViewPager(pager)

        checkPagerState()
    }

    private fun initBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nasa_earth_image -> {
                    binding.viewPager.currentItem = EARTH_KEY
                    true
                }
                R.id.nasa_mars_image -> {
                    binding.viewPager.currentItem = MARS_KEY
                    true
                }
                R.id.nasa_solar_system_image -> {
                    binding.viewPager.currentItem = SOLAR_SYSTEM_KEY
                    true
                }
                else -> true
            }
        }
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

    private fun checkPagerState() {
        pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                binding.bottomNavigationView.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }
}