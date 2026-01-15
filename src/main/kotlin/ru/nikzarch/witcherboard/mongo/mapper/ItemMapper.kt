package ru.nikzarch.witcherboard.mongo.mapper

import org.springframework.stereotype.Component
import ru.nikzarch.witcherboard.mongo.document.ItemDocument
import ru.nikzarch.witcherboard.mongo.dto.response.ItemResponse


@Component
class ItemMapper {
    fun toDto(itemDocument: ItemDocument): ItemResponse = ItemResponse(
        id = itemDocument.id ?: "null",
        name = itemDocument.name,
        description = itemDocument.description,
        price = itemDocument.price,
        type = itemDocument.type,
        mageId = itemDocument.mageId,
        monsterBonuses = itemDocument.monsterBonuses,
        available = itemDocument.available
    )
}
