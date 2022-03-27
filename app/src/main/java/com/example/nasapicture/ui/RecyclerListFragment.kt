package com.example.nasapicture.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.nasapicture.R
import com.example.nasapicture.databinding.FragmentRecyclerListBinding
import com.example.nasapicture.repository.*

class RecyclerListFragment : Fragment() {

    private var _binding: FragmentRecyclerListBinding? = null
    private val binding get() = _binding!!

    private var flag = false

    lateinit var itemTouchHelper: ItemTouchHelper

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

        val planetListData = initialData()

        planetListData.shuffle()

        planetListData.add(
            0,
            Pair(PlanetData(getString(R.string.planets_header), type = TYPE_HEADER, weight = 2), false)
        )

        val adapter = RecyclerListFragmentAdapter(object : OnListItemClickListener {
            override fun onItemClick(data: PlanetData) {
                Toast.makeText(
                    requireContext(),
                    "Clicked on ${data.planetName}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

        }, object : OnStartDragListener {
            override fun onStartDrag(rvViewHolder: RecyclerView.ViewHolder) {
                itemTouchHelper.startDrag(rvViewHolder)
            }
        })

        adapter.setPlanetListData(planetListData)
        binding.rvPlanets.adapter = adapter

        binding.fabNewPlanetItem.setOnClickListener {
            flag = !flag
            fabAnimation()
        }
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.rvPlanets)

        binding.optionEarthContainer.setOnClickListener {
            adapter.appendItem(TYPE_EARTH)
            binding.fabNewPlanetItem.callOnClick()
            binding.rvPlanets.smoothScrollToPosition(planetListData.size - 1)
        }

        binding.optionMarsContainer.setOnClickListener {
            adapter.appendItem(TYPE_MARS)
            binding.fabNewPlanetItem.callOnClick()
            binding.rvPlanets.smoothScrollToPosition(adapter.itemCount)
        }

    }

    class ItemTouchHelperCallback(private val recyclerListFragmentAdapter: RecyclerListFragmentAdapter) :
        ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlag = ItemTouchHelper.START or ItemTouchHelper.END
            return makeMovementFlags(dragFlag, swipeFlag)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            from: RecyclerView.ViewHolder,
            to: RecyclerView.ViewHolder
        ): Boolean {
            if (to.absoluteAdapterPosition > 0) {
                recyclerListFragmentAdapter.onItemMove(
                    from.absoluteAdapterPosition,
                    to.absoluteAdapterPosition
                )
            }
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            recyclerListFragmentAdapter.onItemRemove(viewHolder.absoluteAdapterPosition)
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                when (viewHolder) {
                    is RecyclerListFragmentAdapter.EarthViewHolder -> (viewHolder as RecyclerListFragmentAdapter.EarthViewHolder).onItemSelected()
                    is RecyclerListFragmentAdapter.MarsViewHolder -> (viewHolder as RecyclerListFragmentAdapter.MarsViewHolder).onItemSelected()
                }
            }
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            when (viewHolder) {
                is RecyclerListFragmentAdapter.EarthViewHolder -> (viewHolder as RecyclerListFragmentAdapter.EarthViewHolder).onItemClear()
                is RecyclerListFragmentAdapter.MarsViewHolder -> (viewHolder as RecyclerListFragmentAdapter.MarsViewHolder).onItemClear()
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = RecyclerListFragment()
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

    private fun initialData(): MutableList<Pair<PlanetData, Boolean>> {
        return mutableListOf(
            Pair(
                PlanetData(
                    getString(R.string.earth_title),
                    R.drawable.bg_earth,
                    "Some description",
                    TYPE_EARTH
                ), false
            ),
            Pair(
                PlanetData(
                    getString(R.string.earth_title),
                    R.drawable.bg_earth,
                    "Some description",
                    TYPE_EARTH
                ), false
            ),
            Pair(
                PlanetData(
                    getString(R.string.earth_title),
                    R.drawable.bg_earth,
                    "Some description",
                    TYPE_EARTH
                ), false
            ),
            Pair(
                PlanetData(
                    getString(R.string.earth_title),
                    R.drawable.bg_earth,
                    "Some description",
                    TYPE_EARTH
                ), false
            ),
            Pair(
                PlanetData(getString(R.string.mars_title), R.drawable.bg_mars, type = TYPE_MARS),
                false
            ),
            Pair(
                PlanetData(getString(R.string.mars_title), R.drawable.bg_mars, type = TYPE_MARS),
                false
            ),
            Pair(
                PlanetData(getString(R.string.mars_title), R.drawable.bg_mars, type = TYPE_MARS),
                false
            )
        )
    }

    private fun fabAnimation() {
        val t1 = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0f, -300f)
        val t2 = PropertyValuesHolder.ofFloat(View.SCALE_X, 0f, 1f)
        val t3 = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0f, -180f)
        val t4 = PropertyValuesHolder.ofFloat(View.SCALE_X, 0f, 1f)
        val t5 = PropertyValuesHolder.ofFloat(View.ROTATION, 0f, 405f)

        val animatorEarth =
            ObjectAnimator.ofPropertyValuesHolder(binding.optionEarthContainer, t1, t2)
        val animatorMars =
            ObjectAnimator.ofPropertyValuesHolder(binding.optionMarsContainer, t3, t4)
        val animatorFab = ObjectAnimator.ofPropertyValuesHolder(binding.fabNewPlanetItem, t5)

        animatorEarth.duration = duration
        animatorMars.duration = duration
        animatorFab.duration = duration

        if (flag) {
            animatorFab.start()
            animatorEarth.start()
            animatorMars.start()

            viewAnimate(binding.transparentScreen, 0.6f, false)
            viewAnimate(binding.optionEarthContainer, 1f, true)
            viewAnimate(binding.optionMarsContainer, 1f, true)

        } else {
            animatorFab.reverse()
            animatorEarth.reverse()
            animatorMars.reverse()

            viewAnimate(binding.transparentScreen, 0f, false)
            viewAnimate(binding.optionEarthContainer, 0f, true)
            viewAnimate(binding.optionMarsContainer, 0f, true)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}