package ru.nikzarch.witcherboard.mongo.dto.response

import ru.nikzarch.witcherboard.mongo.document.enum.ItemType

data class ItemResponse(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    val type: ItemType,
    val mageId: Long,
    val monsterBonuses: Map<String, Int> = emptyMap(), // name to chance
    val available: Boolean = true
)