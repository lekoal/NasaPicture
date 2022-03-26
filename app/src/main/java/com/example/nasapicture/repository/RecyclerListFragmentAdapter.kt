package com.example.nasapicture.repository

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.nasapicture.R
import com.example.nasapicture.databinding.FragmentRecyclerEarthItemBinding
import com.example.nasapicture.databinding.FragmentRecyclerHeaderItemBinding
import com.example.nasapicture.databinding.FragmentRecyclerMarsItemBinding

class RecyclerListFragmentAdapter(
    private val onListItemClickListener: OnListItemClickListener
) : RecyclerView.Adapter<RecyclerListFragmentAdapter.BaseViewHolder>(), ItemTouchHelperAdapter {

    private var planetListData: MutableList<Pair<PlanetData, Boolean>> = mutableListOf()

    fun setPlanetListData(listData: MutableList<Pair<PlanetData, Boolean>>) {
        this.planetListData = listData
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

    override fun getItemViewType(position: Int) = planetListData[position].first.type

    override fun getItemCount() = planetListData.size

    fun appendItem(type: Int) {
        planetListData.add(Pair(generatePlanetData(type), false))
        notifyItemInserted(planetListData.size - 1)
    }

    private fun generatePlanetData(type: Int): PlanetData {
        return when (type) {
            TYPE_EARTH -> PlanetData("Земля", R.drawable.bg_earth, "Some description", TYPE_EARTH)
            else -> PlanetData("Марс", R.drawable.bg_mars, type = TYPE_MARS)
        }
    }

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(data: Pair<PlanetData, Boolean>)
    }

    inner class EarthViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        override fun bind(data: Pair<PlanetData, Boolean>) {
            FragmentRecyclerEarthItemBinding.bind(itemView).apply {
                earthName.text = data.first.planetName
                earthDescription.text = data.first.planetDescription

                wikiImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data.first)
                }
                addItemImageView.setOnClickListener {
                    planetListData.add(layoutPosition + 1, Pair(generatePlanetData(TYPE_EARTH), false))
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
                tvEarthDescription.visibility = if (planetListData[layoutPosition].second) View.VISIBLE else View.GONE
                openDescription.setOnClickListener {
                    planetListData[layoutPosition] = planetListData[layoutPosition].let {
                        it.first to !it.second
                    }
                    notifyItemChanged(layoutPosition)
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
                marsName.text = data.first.planetName

                marsImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data.first)
                }
                addItemImageView.setOnClickListener {
                    planetListData.add(layoutPosition + 1, Pair(generatePlanetData(TYPE_MARS), false))
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
                tvMarsDescription.visibility = if (planetListData[layoutPosition].second) View.VISIBLE else View.GONE
                openDescription.setOnClickListener {
                    planetListData[layoutPosition] = planetListData[layoutPosition].let {
                        it.first to !it.second
                    }
                    notifyItemChanged(layoutPosition)
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

    override fun onItemRemove(position: Int) {
        planetListData.removeAt(position)
        notifyItemRemoved(position)
    }
}