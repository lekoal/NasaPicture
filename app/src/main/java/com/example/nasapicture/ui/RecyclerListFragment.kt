package com.example.nasapicture.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Property
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nasapicture.R
import com.example.nasapicture.databinding.FragmentRecyclerListBinding
import com.example.nasapicture.repository.*

class RecyclerListFragment : Fragment() {

    private var _binding: FragmentRecyclerListBinding? = null
    private val binding get() = _binding!!

    private var flag = false

    private val duration = 1000L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val planetListData = arrayListOf(
            PlanetData(getString(R.string.earth_title), "Some description", TYPE_EARTH),
            PlanetData(getString(R.string.earth_title), "Some description", TYPE_EARTH),
            PlanetData(getString(R.string.earth_title), "Some description", TYPE_EARTH),
            PlanetData(getString(R.string.earth_title), "Some description", TYPE_EARTH),
            PlanetData(getString(R.string.mars_title), type = TYPE_MARS),
            PlanetData(getString(R.string.mars_title), type = TYPE_MARS),
            PlanetData(getString(R.string.mars_title), type = TYPE_MARS)
        )

        planetListData.shuffle()

        planetListData.add(0, PlanetData(getString(R.string.planets_header), type = TYPE_HEADER))

        val adapter = RecyclerListFragmentAdapter {
            Toast.makeText(requireContext(), "Clicked on ${it.planetName}", Toast.LENGTH_SHORT).show()
        }
        adapter.setPlanetListData(planetListData)
        binding.rvPlanets.adapter = adapter

        binding.fabNewPlanetItem.setOnClickListener {
            flag = !flag
            if (flag) {
                objectAnimatorStart(binding.fabNewPlanetItem, View.ROTATION, 0f, 405f)
                objectAnimatorStart(binding.optionEarthContainer, View.TRANSLATION_Y, 0f, -250f)
                objectAnimatorStart(binding.optionEarthContainer, View.SCALE_X, 0f, 1f)
                objectAnimatorStart(binding.optionMarsContainer, View.TRANSLATION_Y, 0f, -160f)
                objectAnimatorStart(binding.optionMarsContainer, View.SCALE_X, 0f, 1f)

                viewAnimate(binding.transparentScreen, 0.6f, false)
                viewAnimate(binding.optionEarthContainer, 1f, true)
                viewAnimate(binding.optionMarsContainer, 1f, true)

            } else {
                objectAnimatorStart(binding.fabNewPlanetItem, View.ROTATION, 405f, 0f)
                objectAnimatorStart(binding.optionEarthContainer, View.TRANSLATION_Y, -250f, 0f)
                objectAnimatorStart(binding.optionEarthContainer, View.SCALE_X, 1f, 0f)
                objectAnimatorStart(binding.optionMarsContainer, View.TRANSLATION_Y, -160f, 0f)
                objectAnimatorStart(binding.optionMarsContainer, View.SCALE_X, 1f, 0f)

                viewAnimate(binding.transparentScreen, 0f, false)
                viewAnimate(binding.optionEarthContainer, 0f, true)
                viewAnimate(binding.optionMarsContainer, 0f, true)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RecyclerListFragment()
    }

    private fun objectAnimatorStart(
        view: View,
        property: Property<View, Float>,
        start: Float,
        end: Float
    ) {
        ObjectAnimator.ofFloat(view, property, start, end).setDuration(duration).start()
    }

    private fun viewAnimate(view: View, alpha: Float, needClickable: Boolean) {
        view.animate()
            .alpha(alpha)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    view.isClickable = needClickable && flag
                }
            })
            .start()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}