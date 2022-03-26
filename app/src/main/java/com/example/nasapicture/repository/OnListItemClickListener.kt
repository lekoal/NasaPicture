package com.example.nasapicture.repository

import androidx.recyclerview.widget.RecyclerView

fun interface OnListItemClickListener {
    fun onItemClick(data: PlanetData)
}

fun interface OnStartDragListener {
    fun onStartDrag(view: RecyclerView.ViewHolder)
}