package com.example.nasapicture.repository

data class NasaImageDTO(
    val collection: Collection?
) {
    data class Collection(
        val href: String?,
        val items: List<Item?>?,
        val version: String?
    ) {
        data class Item(
            val href: String?
        )
    }
}