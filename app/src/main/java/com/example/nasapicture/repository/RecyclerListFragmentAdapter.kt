package com.example.nasapicture.repository

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nasapicture.databinding.FragmentRecyclerEarthItemBinding
import com.example.nasapicture.databinding.FragmentRecyclerMarsItemBinding

class RecyclerListFragmentAdapter(
    private val onListItemClickListener: OnListItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var planetListData: MutableList<PlanetData> = mutableListOf()

    fun setPlanetListData(listData: MutableList<PlanetData>) {
        this.planetListData = listData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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
            else -> {
                MarsViewHolder(
                    FragmentRecyclerMarsItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ).root
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_EARTH -> {
                holder as EarthViewHolder
                holder.bind(planetListData[position])
            }
            else -> {
                holder as MarsViewHolder
                holder.bind(planetListData[position])
            }
        }
    }

    override fun getItemViewType(position: Int) = planetListData[position].type

    override fun getItemCount() = planetListData.size

    inner class EarthViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: PlanetData) {
            FragmentRecyclerEarthItemBinding.bind(itemView).apply {
                earthName.text = data.planetName
                earthDescription.text = data.planetDescription
                wikiImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: PlanetData) {
            FragmentRecyclerMarsItemBinding.bind(itemView).apply {
                marsName.text = data.planetName
                marsImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
                }
            }
        }
    }
}