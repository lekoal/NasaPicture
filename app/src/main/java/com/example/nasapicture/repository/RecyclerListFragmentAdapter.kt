package com.example.nasapicture.repository

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.nasapicture.R
import com.example.nasapicture.databinding.FragmentRecyclerEarthItemBinding
import com.example.nasapicture.databinding.FragmentRecyclerHeaderItemBinding
import com.example.nasapicture.databinding.FragmentRecyclerMarsItemBinding
import com.example.nasapicture.ui.diffutils.Change
import com.example.nasapicture.ui.diffutils.DiffUtilsCallback
import com.example.nasapicture.ui.diffutils.createCombinePayloads

class RecyclerListFragmentAdapter(
    private val onListItemClickListener: OnListItemClickListener,
    private val onStartDragListener: OnStartDragListener
) : RecyclerView.Adapter<RecyclerListFragmentAdapter.BaseViewHolder>(), ItemTouchHelperAdapter {

    private var planetListData: MutableList<Pair<PlanetData, Boolean>> = mutableListOf()

    fun setPlanetListData(listData: MutableList<Pair<PlanetData, Boolean>>) {
        val res = DiffUtil.calculateDiff(DiffUtilsCallback(planetListData, listData))
        res.dispatchUpdatesTo(this)
        planetListData.clear()
        planetListData.addAll(listData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TYPE_EARTH -> {
                EarthViewHolder(
                    FragmentRecyclerEarthItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ).root
                )
            }
            TYPE_MARS -> {
                MarsViewHolder(
                    FragmentRecyclerMarsItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ).root
                )
            }
            else -> {
                HeaderViewHolder(
                    FragmentRecyclerHeaderItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ).root
                )
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(planetListData[position])
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {

            val oldData =
                createCombinePayloads(payloads as MutableList<Change<Pair<PlanetData, Boolean>>>).oldData
            val newData =
                createCombinePayloads(payloads as MutableList<Change<Pair<PlanetData, Boolean>>>).newData

            if (payloads[position].oldData.first.type == TYPE_EARTH) {
                if (newData.first.planetName != oldData.first.planetName) {
                    FragmentRecyclerEarthItemBinding.bind(holder.itemView).earthName.text =
                        newData.first.planetName
                }
                if (newData.first.planetDescription != oldData.first.planetDescription) {
                    FragmentRecyclerEarthItemBinding.bind(holder.itemView).tvEarthDescription.text =
                        newData.first.planetDescription
                }
            }
            if (payloads[position].oldData.first.type == TYPE_MARS) {
                if (newData.first.planetName != oldData.first.planetName) {
                    FragmentRecyclerMarsItemBinding.bind(holder.itemView).marsName.text =
                        newData.first.planetName
                }
                if (newData.first.planetDescription != oldData.first.planetDescription) {
                    FragmentRecyclerMarsItemBinding.bind(holder.itemView).tvMarsDescription.text =
                        newData.first.planetDescription
                }
            }

        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemViewType(position: Int) = planetListData[position].first.type

    override fun getItemCount() = planetListData.size

    fun appendItem(type: Int) {
        planetListData.add(Pair(generatePlanetData(type), false))
        notifyItemInserted(planetListData.size - 1)
    }

    private fun generatePlanetData(type: Int): PlanetData {
        var temp = 0
        planetListData.forEach {
            if (it.first.id > temp) temp = it.first.id
        }
        return when (type) {
            TYPE_EARTH -> PlanetData(
                temp + 1,
                "Земля",
                R.drawable.bg_earth,
                "Some description",
                TYPE_EARTH
            )
            else -> PlanetData(temp + 1, "Марс", R.drawable.bg_mars, type = TYPE_MARS)
        }
    }

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(data: Pair<PlanetData, Boolean>)
    }

    inner class EarthViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        override fun bind(data: Pair<PlanetData, Boolean>) {
            FragmentRecyclerEarthItemBinding.bind(itemView).apply {

                starSwitcher(layoutPosition, this.addToFavorite)

                earthName.text = data.first.planetName
                earthDescription.text = data.first.planetDescription

                wikiImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data.first)
                }
                addItemImageView.setOnClickListener {
                    planetListData.add(
                        layoutPosition + 1,
                        Pair(generatePlanetData(TYPE_EARTH), false)
                    )
                    notifyItemInserted(layoutPosition + 1)
                }
                removeItemImageView.setOnClickListener {
                    planetListData.removeAt(layoutPosition)
                    notifyItemRemoved(layoutPosition)
                }
                moveItemUp.setOnClickListener {
                    if (layoutPosition != 1) {
                        planetListData.removeAt(layoutPosition).apply {
                            planetListData.add(layoutPosition - 1, this)
                            notifyItemMoved(layoutPosition, layoutPosition - 1)
                        }
                    }
                }
                moveItemDown.setOnClickListener {
                    if (layoutPosition != planetListData.size - 1) {
                        planetListData.removeAt(layoutPosition).apply {
                            planetListData.add(layoutPosition + 1, this)
                            notifyItemMoved(layoutPosition, layoutPosition + 1)
                        }
                    }
                }
                tvEarthDescription.visibility =
                    if (planetListData[layoutPosition].second) View.VISIBLE else View.GONE
                openDescription.setOnClickListener {
                    planetListData[layoutPosition] = planetListData[layoutPosition].let {
                        it.first to !it.second
                    }
                    notifyItemChanged(layoutPosition)
                }

                addToFavorite.setOnClickListener {
                    changeWeight(layoutPosition)
                }

                dragHandleImageView.setOnTouchListener { v, event ->
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        onStartDragListener.onStartDrag(this@EarthViewHolder)
                    }
                    false
                }
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.WHITE)
        }
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        override fun bind(data: Pair<PlanetData, Boolean>) {
            FragmentRecyclerMarsItemBinding.bind(itemView).apply {

                starSwitcher(layoutPosition, this.addToFavorite)

                marsName.text = data.first.planetName

                marsImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data.first)
                }
                addItemImageView.setOnClickListener {
                    planetListData.add(
                        layoutPosition + 1,
                        Pair(generatePlanetData(TYPE_MARS), false)
                    )
                    notifyItemInserted(layoutPosition + 1)
                }
                removeItemImageView.setOnClickListener {
                    planetListData.removeAt(layoutPosition)
                    notifyItemRemoved(layoutPosition)
                }
                moveItemUp.setOnClickListener {
                    if (layoutPosition != 1) {
                        planetListData.removeAt(layoutPosition).apply {
                            planetListData.add(layoutPosition - 1, this)
                            notifyItemMoved(layoutPosition, layoutPosition - 1)
                        }
                    }
                }
                moveItemDown.setOnClickListener {
                    if (layoutPosition != planetListData.size - 1) {
                        planetListData.removeAt(layoutPosition).apply {
                            planetListData.add(layoutPosition + 1, this)
                            notifyItemMoved(layoutPosition, layoutPosition + 1)
                        }
                    }
                }
                tvMarsDescription.visibility =
                    if (planetListData[layoutPosition].second) View.VISIBLE else View.GONE
                openDescription.setOnClickListener {
                    planetListData[layoutPosition] = planetListData[layoutPosition].let {
                        it.first to !it.second
                    }
                    notifyItemChanged(layoutPosition)
                }

                addToFavorite.setOnClickListener {
                    changeWeight(layoutPosition)
                }

                dragHandleImageView.setOnTouchListener { v, event ->
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        onStartDragListener.onStartDrag(this@MarsViewHolder)
                    }
                    false
                }
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.WHITE)
        }
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<PlanetData, Boolean>) {
            FragmentRecyclerHeaderItemBinding.bind(itemView).apply {
                header.text = data.first.planetName
                header.setOnClickListener {
                    onListItemClickListener.onItemClick(data.first)
                }
            }
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        planetListData.removeAt(fromPosition).apply {
            planetListData.add(toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    private fun changeWeight(layoutPosition: Int) {
        val tempId = planetListData[layoutPosition].first.id
        val tempName = planetListData[layoutPosition].first.planetName
        val tempImage = planetListData[layoutPosition].first.planetImage
        val tempDescription = planetListData[layoutPosition].first.planetDescription
        val tempType = planetListData[layoutPosition].first.type
        val weight = if (planetListData[layoutPosition].first.weight == 0) 1 else 0
        val tempPlanetData: Pair<PlanetData, Boolean> = Pair(
            PlanetData(
                tempId,
                tempName,
                tempImage,
                tempDescription,
                tempType,
                weight
            ), false
        )
        planetListData.removeAt(layoutPosition)
        planetListData.add(layoutPosition, tempPlanetData)
        planetListData.sortByDescending { it.first.weight }
        notifyItemRangeChanged(1, layoutPosition + 1)
    }

    private fun starSwitcher(position: Int, view: AppCompatImageView) {
        if (planetListData[position].first.weight == 1) view.load(R.drawable.ic_baseline_star_24)
        else view.load(R.drawable.ic_baseline_star_border_24)
    }

    override fun onItemRemove(position: Int) {
        planetListData.removeAt(position)
        notifyItemRemoved(position)
    }
}