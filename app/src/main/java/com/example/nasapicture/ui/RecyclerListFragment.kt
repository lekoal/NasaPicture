package com.example.nasapicture.ui

import android.os.Bundle
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
    }

    companion object {
        @JvmStatic
        fun newInstance() = RecyclerListFragment()
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}