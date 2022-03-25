package com.example.nasapicture.repository

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nasapicture.R
import com.example.nasapicture.databinding.FragmentRecyclerEarthItemBinding
import com.example.nasapicture.databinding.FragmentRecyclerHeaderItemBinding
import com.example.nasapicture.databinding.FragmentRecyclerMarsItemBinding
import com.example.nasapicture.ui.EARTH_KEY

class RecyclerListFragmentAdapter(
    private val onListItemClickListener: OnListItemClickListener
) : RecyclerView.Adapter<RecyclerListFragmentAdapter.BaseViewHolder>() {

    private var planetListData: MutableList<PlanetData> = mutableListOf()

    fun setPlanetListData(listData: MutableList<PlanetData>) {
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

    override fun getItemViewType(position: Int) = planetListData[position].type

    override fun getItemCount() = planetListData.size

    fun appendItem(type: Int) {
        planetListData.add(generatePlanetData(type))
        notifyItemInserted(planetListData.size - 1)
    }

    private fun generatePlanetData(type: Int): PlanetData {
        return when (type) {
            TYPE_EARTH -> PlanetData("Земля", R.drawable.bg_earth, "Some description", TYPE_EARTH)
            else -> PlanetData("Марс", R.drawable.bg_mars, type = TYPE_MARS)
        }
    }

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(data: PlanetData)
    }

    inner class EarthViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: PlanetData) {
            FragmentRecyclerEarthItemBinding.bind(itemView).apply {
                earthName.text = data.planetName
                earthDescription.text = data.planetDescription
                if (layoutPosition == 1) moveItemUp.visibility = View.GONE
                else moveItemUp.visibility = View.VISIBLE
                if (layoutPosition == planetListData.size - 1) moveItemDown.visibility = View.GONE
                else moveItemDown.visibility = View.VISIBLE
                wikiImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
                }
                addItemImageView.setOnClickListener {
                    planetListData.add(layoutPosition + 1, generatePlanetData(TYPE_EARTH))
                    notifyItemInserted(layoutPosition + 1)
                }
                removeItemImageView.setOnClickListener {
                    planetListData.removeAt(layoutPosition)
                    notifyItemRemoved(layoutPosition)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: PlanetData) {
            FragmentRecyclerMarsItemBinding.bind(itemView).apply {
                marsName.text = data.planetName
                if (layoutPosition == 1) moveItemUp.visibility = View.GONE
                else moveItemUp.visibility = View.VISIBLE
                if (layoutPosition == planetListData.size - 1) moveItemDown.visibility = View.GONE
                else moveItemDown.visibility = View.VISIBLE
                marsImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
                }
                addItemImageView.setOnClickListener {
                    planetListData.add(layoutPosition + 1, generatePlanetData(TYPE_MARS))
                    notifyItemInserted(layoutPosition + 1)
                }
                removeItemImageView.setOnClickListener {
                    planetListData.removeAt(layoutPosition)
                    notifyItemRemoved(layoutPosition)
                }
            }
        }
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: PlanetData) {
            FragmentRecyclerHeaderItemBinding.bind(itemView).apply {
                header.text = data.planetName
                header.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
                }
            }
        }
    }
}