package com.example.nasapicture.repository

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nasapicture.databinding.FragmentRecyclerEarthItemBinding
import com.example.nasapicture.databinding.FragmentRecyclerHeaderItemBinding
import com.example.nasapicture.databinding.FragmentRecyclerMarsItemBinding

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

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(data: PlanetData)
    }

    inner class EarthViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: PlanetData) {
            FragmentRecyclerEarthItemBinding.bind(itemView).apply {
                earthName.text = data.planetName
                earthDescription.text = data.planetDescription
                wikiImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: PlanetData) {
            FragmentRecyclerMarsItemBinding.bind(itemView).apply {
                marsName.text = data.planetName
                marsImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
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