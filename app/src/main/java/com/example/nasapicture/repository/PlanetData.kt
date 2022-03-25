package com.example.nasapicture.repository

const val TYPE_EARTH = 1
const val TYPE_MARS = 2
const val TYPE_HEADER = 3

data class PlanetData(
    val planetName: String = "Name",
    var planetDescription: String? = null,
    val type: Int = 0
)
