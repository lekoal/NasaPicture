package com.example.nasapicture.repository

const val TYPE_EARTH = 1
const val TYPE_MARS = 2
const val TYPE_HEADER = 3

data class PlanetData(
    val id: Int = 0,
    val planetName: String = "Name",
    val planetImage: Int? = null,
    var planetDescription: String? = null,
    val type: Int = 0,
    val weight: Int = 0
)
