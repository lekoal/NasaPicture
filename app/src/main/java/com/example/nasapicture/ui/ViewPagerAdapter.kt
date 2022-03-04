package com.example.nasapicture.ui

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.nasapicture.ui.navigation.EarthFragment
import com.example.nasapicture.ui.navigation.MarsFragment
import com.example.nasapicture.ui.navigation.SolarSystemFragment

const val EARTH_KEY = 0
const val MARS_KEY = 1
const val SOLAR_SYSTEM_KEY = 2

class ViewPagerAdapter(private val fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = arrayOf(EarthFragment(), MarsFragment(), SolarSystemFragment())

    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]

    override fun getPageTitle(position: Int): CharSequence {
        return when(position) {
            EARTH_KEY -> "Earth"
            MARS_KEY -> "Mars"
            SOLAR_SYSTEM_KEY -> "System"
            else -> "Earth"
        }
    }

}