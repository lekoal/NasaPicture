package com.example.nasapicture.ui.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.nasapicture.repository.PlanetData

class DiffUtilsCallback(
    private val oldItems: MutableList<Pair<PlanetData, Boolean>>,
    private val newItems: MutableList<Pair<PlanetData, Boolean>>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].first.id == newItems[newItemPosition].first.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldItems[oldItemPosition].first.planetName == newItems[newItemPosition].first.planetName) &&
                (oldItems[oldItemPosition].first.planetDescription == newItems[newItemPosition].first.planetDescription)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return Change(oldItem, newItem)
    }
}