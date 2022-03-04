package com.example.nasapicture.ui.navigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.nasapicture.R
import com.example.nasapicture.databinding.FragmentEarthBinding
import com.example.nasapicture.viewmodel.PictureOfTheDayState
import com.example.nasapicture.viewmodel.PictureOfTheDayViewModel

class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private val binding get(): FragmentEarthBinding = _binding!!

    private val nasaId = "PIA18033"

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this)[PictureOfTheDayViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = EarthFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.sendServerRequestForImage(nasaId)
    }

    private fun renderData(pictureOfTheDayState: PictureOfTheDayState?) {

        val bottomNavigationActivity = activity as BottomNavigationActivity
        val loadingLayout = bottomNavigationActivity.findViewById<View>(R.id.loadingLayout)

        when (pictureOfTheDayState) {
            is PictureOfTheDayState.Loading -> {
                loadingLayout.visibility = View.VISIBLE
            }
            is PictureOfTheDayState.SuccessImage -> {
                loadingLayout.visibility = View.GONE
                binding.earthImage.load(
                    pictureOfTheDayState.serverResponseImageData.collection?.items?.get(0)?.href
                )
            }
            is PictureOfTheDayState.Error -> {
                loadingLayout.visibility = View.GONE
                with(loadingLayout) {
                    Toast.makeText(context, getString(R.string.live_data_error), Toast.LENGTH_SHORT)
                        .show()
                }
            }
            else -> {}
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}