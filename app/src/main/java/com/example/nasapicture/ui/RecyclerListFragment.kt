package com.example.nasapicture.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nasapicture.databinding.FragmentRecyclerListBinding
import com.example.nasapicture.repository.PlanetData
import com.example.nasapicture.repository.TYPE_EARTH
import com.example.nasapicture.repository.TYPE_MARS

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

        val data = arrayListOf(
            PlanetData("Earth", "Some description", TYPE_EARTH),
            PlanetData("Earth", "Some description", TYPE_EARTH),
            PlanetData("Earth", "Some description", TYPE_EARTH),
            PlanetData("Earth", "Some description", TYPE_EARTH),
            PlanetData("Mars", type = TYPE_MARS),
            PlanetData("Mars", type = TYPE_MARS),
            PlanetData("Mars", type = TYPE_MARS)

        )
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