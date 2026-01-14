package ru.nikzarch.witcherboard.mongo.service

import ru.nikzarch.witcherboard.mongo.document.InventoryDocument

interface InventoryService {
    fun addItem(witcherId: Long, itemId: String): InventoryDocument
    fun removeItem(witcherId: Long, itemId: String)
    fun getByWitcherId(witcherId: Long): InventoryDocument
}
