package com.example.nasapicture.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.nasapicture.R
import com.example.nasapicture.databinding.PictureOfTheDayFragmentBinding
import com.example.nasapicture.viewmodel.PictureOfTheDayState
import com.example.nasapicture.viewmodel.PictureOfTheDayViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar

class PictureOfTheDayFragment : Fragment() {

    private var _binding: PictureOfTheDayFragmentBinding? = null
    private val binding: PictureOfTheDayFragmentBinding get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this)[PictureOfTheDayViewModel::class.java]
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PictureOfTheDayFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.sendServerRequest()

        setBottomSheetBehavior(binding.included.bottomSheetContainer)
    }

    private fun renderData(pictureOfTheDayState: PictureOfTheDayState?) {
        when (pictureOfTheDayState) {
            is PictureOfTheDayState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is PictureOfTheDayState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                binding.imageView.load(pictureOfTheDayState.serverResponseData.hdurl)
                binding.included.bottomSheetDescriptionHeader.text = pictureOfTheDayState.serverResponseData.title
                binding.included.bottomSheetDescription.text = pictureOfTheDayState.serverResponseData.explanation
            }
            is PictureOfTheDayState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                with(binding.loadingLayout) {
                    showActionSnackBar(
                        R.string.snack_bar_error_text,
                        R.string.snack_bar_action_text,
                        pictureOfTheDayState
                    )
                }
            }
            else -> {}
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun View.showActionSnackBar(
        text: Int,
        actionText: Int,
        appState: PictureOfTheDayState,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText) {
            viewModel.sendServerRequest()
            renderData(appState)
        }.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}