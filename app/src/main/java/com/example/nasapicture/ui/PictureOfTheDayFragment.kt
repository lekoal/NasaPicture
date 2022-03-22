package com.example.nasapicture.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeImageTransform
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import com.example.nasapicture.R
import com.example.nasapicture.databinding.PictureOfTheDayFragmentBinding
import com.example.nasapicture.ui.navigation.BottomNavigationActivity
import com.example.nasapicture.viewmodel.PictureOfTheDayState
import com.example.nasapicture.viewmodel.PictureOfTheDayViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayFragment : Fragment() {

    private var _binding: PictureOfTheDayFragmentBinding? = null
    private val binding: PictureOfTheDayFragmentBinding get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this)[PictureOfTheDayViewModel::class.java]
    }

    private var flag = false

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val cal = Calendar.getInstance()
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private lateinit var yesterdayDate: String
    private lateinit var beforeYesterdayDate: String

    private lateinit var youTubePlayerView: YouTubePlayerView

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        var isMain: Boolean = true
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

        youTubePlayerView = view.findViewById(R.id.video_view)
        lifecycle.addObserver(youTubePlayerView)

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

        val transition = TransitionSet()
        val fade = Fade()
        val changeImageTransform = ChangeImageTransform()
        changeImageTransform.duration = 500
        fade.duration = 1000
        transition.addTransition(fade)

        when (pictureOfTheDayState) {
            is PictureOfTheDayState.Loading -> {
                TransitionManager.beginDelayedTransition(binding.loadingLayout, TransitionSet()
                    .addTransition(transition)
                    .addTransition(changeImageTransform))
                binding.loadingErrorImage.load(R.drawable.loading_text)
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is PictureOfTheDayState.Success -> {
                TransitionManager.endTransitions(binding.loadingLayout)
                binding.loadingLayout.visibility = View.GONE
                if (pictureOfTheDayState.serverResponseData.mediaType == "image") {
                    binding.videoView.visibility = View.GONE
                    binding.imageView.visibility = View.GONE

                    binding.imageView.load(pictureOfTheDayState.serverResponseData.hdurl)
                    binding.imageView.visibility = View.VISIBLE

                    binding.imageView.setOnClickListener {
                        flag = !flag
                        TransitionManager.beginDelayedTransition(binding.transitionContainer, changeImageTransform)
                        binding.imageView.scaleType = if (flag) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.CENTER_INSIDE
                    }

                } else if (pictureOfTheDayState.serverResponseData.mediaType == "video") {
                    binding.videoView.visibility = View.VISIBLE
                    binding.imageView.visibility = View.GONE

                    youTubePlayerView.addYouTubePlayerListener(object :
                        AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            super.onReady(youTubePlayer)
                            val videoId =
                                youTUbeIdCutter(pictureOfTheDayState.serverResponseData.hdurl)
                            youTubePlayer.loadVideo(videoId, 0F)
                        }
                    })
                }
                binding.included.bottomSheetDescriptionHeader.text =
                    pictureOfTheDayState.serverResponseData.title
                binding.included.bottomSheetDescription.text =
                    pictureOfTheDayState.serverResponseData.explanation
            }
            is PictureOfTheDayState.Error -> {
                binding.progressBar.visibility = View.GONE
                binding.loadingLayout.visibility = View.GONE
                binding.loadingErrorImage.load(R.drawable.error_text)
                binding.loadingLayout.visibility = View.VISIBLE
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
        inflater.inflate(R.menu.menu_bottom_app_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_another_activity -> {
                startActivity(Intent(context, BottomNavigationActivity::class.java))
            }
            android.R.id.home -> {
                BottomNavigationDrawerFragment().show(requireActivity().supportFragmentManager, "")
            }
            R.id.app_bar_text -> {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, TextInfoFragment())
                    ?.addToBackStack("")
                    ?.commit()
            }
            R.id.animation_page -> {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, AnimationFragment())
                    ?.addToBackStack("")
                    ?.commit()
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
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
    }

    private fun fabClicker() {
        binding.fab.setOnClickListener {
            if (isMain) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_back_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_menu_24)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_plus_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_app_bar)
            }
            isMain = !isMain
        }
    }

    private fun chipsChanged() {
        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chip_01 -> viewModel.sendServerRequestForDate(yesterdayDate)
                R.id.chip_02 -> viewModel.sendServerRequestForDate(beforeYesterdayDate)
                R.id.chip_03 -> viewModel.sendServerRequest()
            }
        }
    }

    private fun youTUbeIdCutter(url: String): String {

        var videoId: String = ""
        if (url.contains("://youtu.be/")) {
            videoId = url.split(".be/")[1]
        } else if (url.contains("://youtube.com/watch?")) {
            videoId = url.split("\\?v=")[1]
        } else if (url.contains("/embed/") && url.contains("?rel")) {
            videoId = url.split("embed/", "?rel")[1]
        } else if (url.contains("/embed/") && !url.contains("?rel")) {
            videoId = url.split("embed/")[1]
        }
        return videoId
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}