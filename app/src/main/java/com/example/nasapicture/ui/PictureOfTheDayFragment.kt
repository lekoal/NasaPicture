package com.example.nasapicture.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.nasapicture.R
import com.example.nasapicture.databinding.PictureOfTheDayFragmentBinding
import com.example.nasapicture.viewmodel.PictureOfTheDayState
import com.example.nasapicture.viewmodel.PictureOfTheDayViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayFragment : Fragment() {

    private var _binding: PictureOfTheDayFragmentBinding? = null
    private val binding: PictureOfTheDayFragmentBinding get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this)[PictureOfTheDayViewModel::class.java]
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val cal = Calendar.getInstance()
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private lateinit var yesterdayDate: String
    private lateinit var beforeYesterdayDate: String

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        var isMain:Boolean = true
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

        setBottomAppBar(view)

        binding.chipGroup.check(R.id.chip_03)

        searchWiki()

        fabClicker()

        chipsChanged()

        cal.add(Calendar.DATE, -1)
        yesterdayDate = simpleDateFormat.format(cal.time)
        cal.add(Calendar.DATE, -1)
        beforeYesterdayDate = simpleDateFormat.format(cal.time)
    }

    private fun renderData(pictureOfTheDayState: PictureOfTheDayState?) {
        when (pictureOfTheDayState) {
            is PictureOfTheDayState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is PictureOfTheDayState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                binding.imageView.load(pictureOfTheDayState.serverResponseData.hdurl)
                binding.included.bottomSheetDescriptionHeader.text =
                    pictureOfTheDayState.serverResponseData.title
                binding.included.bottomSheetDescription.text =
                    pictureOfTheDayState.serverResponseData.explanation
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.bottom_app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> {
                Toast.makeText(context, "Favourite", Toast.LENGTH_SHORT).show()
            }
            R.id.app_bar_search -> Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show()
            android.R.id.home -> {
                BottomNavigationDrawerFragment().show(requireActivity().supportFragmentManager,"")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
    }

    private fun searchWiki() {
        binding.inputLayout.setEndIconOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
    }

    private fun fabClicker() {
        binding.fab.setOnClickListener{
            if(isMain){
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_back_fab))
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            }else{
                binding.bottomAppBar.navigationIcon = ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_menu_24)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_plus_fab))
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_navigation)
            }
            isMain = !isMain
        }
    }

    private fun chipsChanged() {
        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                R.id.chip_01 -> {
                    viewModel.sendServerRequestForDate(yesterdayDate)
                }
                R.id.chip_02 -> {
                    viewModel.sendServerRequestForDate(beforeYesterdayDate)
                }
                R.id.chip_03 -> viewModel.sendServerRequest()
            }
        }
    }

    private fun selectThemeOnStart() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}