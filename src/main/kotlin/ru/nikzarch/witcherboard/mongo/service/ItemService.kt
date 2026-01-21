package ru.nikzarch.witcherboard.mongo.service

import ru.nikzarch.witcherboard.mongo.document.ItemDocument
import ru.nikzarch.witcherboard.mongo.dto.request.CreateItemRequest
import ru.nikzarch.witcherboard.mongo.dto.request.DeleteItemRequest
import ru.nikzarch.witcherboard.mongo.dto.response.ItemResponse

interface ItemService {

    fun getAll(): List<ItemResponse>

    fun createItem(request: CreateItemRequest): ItemResponse
    fun deleteItem(request: DeleteItemRequest)
    fun getItemById(itemId: String): ItemResponse

    fun getAvailableItemsByMage(mageId: Long): List<ItemDocument>

    fun setAvailability(itemId: String, available: Boolean)

    fun getItemsByWitcherId(witcherId: Long): List<ItemDocument>


}
