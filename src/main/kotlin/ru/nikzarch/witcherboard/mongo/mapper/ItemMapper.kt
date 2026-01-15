package ru.nikzarch.witcherboard.mongo.mapper

import org.springframework.stereotype.Component
import ru.nikzarch.witcherboard.mongo.document.ItemDocument
import ru.nikzarch.witcherboard.mongo.dto.response.ItemResponse
import ru.sinchi.monsterservice.service.MonsterFeatureService
import ru.sinchi.monsterservice.service.MonsterService

@Component
class ItemMapper(
    private val monsterFeatureService: MonsterFeatureService
) {
    fun toDto(itemDocument: ItemDocument): ItemResponse = ItemResponse(
        id = itemDocument.id ?: "null",
        name = itemDocument.name,
        description = itemDocument.description,
        price = itemDocument.price,
        type = itemDocument.type,
        mageId = itemDocument.mageId,
        monsterBonuses = itemDocument.monsterBonuses.mapKeys { (k,v) ->
            monsterFeatureService.getMonsterFeatureById(k).name
        },
        available = itemDocument.available
    )
}