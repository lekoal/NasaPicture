package com.example.nasapicture.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nasapicture.ui.navigation.EarthFragment
import com.example.nasapicture.ui.navigation.MarsFragment
import com.example.nasapicture.ui.navigation.SolarSystemFragment

const val EARTH_KEY = 0
const val MARS_KEY = 1
const val SOLAR_SYSTEM_KEY = 2

class ViewPagerAdapter(private val fragmentManager: FragmentActivity): FragmentStateAdapter(fragmentManager) {

    private val fragments = arrayOf(EarthFragment(), MarsFragment(), SolarSystemFragment())

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

}