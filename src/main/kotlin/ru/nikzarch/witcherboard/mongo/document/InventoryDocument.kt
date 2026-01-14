package ru.nikzarch.witcherboard.mongo.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "inventories")
class InventoryDocument(

    @Id
    val id: String? = null,

    val witcherId: Long,

    val itemIds: MutableList<String> = mutableListOf()
)
