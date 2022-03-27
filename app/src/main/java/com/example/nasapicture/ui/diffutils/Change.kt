package com.example.nasapicture.ui.diffutils

data class Change<out PlanetData>(
    val oldData: PlanetData,
    val newData: PlanetData
)

fun <PlanetData> createCombinePayloads(payloads: List<Change<PlanetData>>): Change<PlanetData> {
    val first = payloads.first()
    val last = payloads.last()
    return Change(first.oldData, last.newData)
}
