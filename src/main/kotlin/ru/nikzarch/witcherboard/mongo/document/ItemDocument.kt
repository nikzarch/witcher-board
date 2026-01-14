package ru.nikzarch.witcherboard.mongo.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.nikzarch.witcherboard.mongo.document.enum.ItemType

@Document(collection = "items")
class ItemDocument(

    @Id
    val id: String? = null,

    var name: String,
    var description: String,
    var price: Int,

    var type: ItemType,

    var mageId: Long,

    /**
     * monsterId -> bonus percent to win
     */
    var monsterBonuses: Map<String, Int> = emptyMap(),

    var available: Boolean = true
)
