package ru.nikzarch.witcherboard.mongo.dto.request

import ru.nikzarch.witcherboard.mongo.document.enum.ItemType

data class CreateItemRequest(
    val name: String,
    val description: String,
    val price: Int,
    val type: ItemType,
    val mageId: Long,
    val monsterBonuses: Map<String, Int> = emptyMap() // monsterName to additional chance for win
)
