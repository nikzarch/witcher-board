package ru.nikzarch.witcherboard.mongo.service.impl

import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.nikzarch.witcherboard.mongo.document.ItemDocument
import ru.nikzarch.witcherboard.mongo.dto.request.CreateItemRequest
import ru.nikzarch.witcherboard.mongo.dto.request.DeleteItemRequest
import ru.nikzarch.witcherboard.mongo.dto.response.ItemResponse
import ru.nikzarch.witcherboard.mongo.mapper.ItemMapper
import ru.nikzarch.witcherboard.mongo.repository.InventoryRepository
import ru.nikzarch.witcherboard.mongo.repository.ItemRepository
import ru.nikzarch.witcherboard.mongo.service.ItemService
import ru.nikzarch.witcherboard.service.UserService

@Service
class ItemServiceImpl(
    private val itemRepository: ItemRepository,
    private val itemMapper: ItemMapper,
    private val userService: UserService,
    private val inventoryRepository: InventoryRepository
) : ItemService {

    override fun getAll(): List<ItemResponse> = itemRepository.findAll().map(itemMapper::toDto)

    override fun createItem(request: CreateItemRequest): ItemResponse {
        userService.findUserById(request.mageId)?:{
            throw UsernameNotFoundException("mage doesnt exist")
        }
        val item = ItemDocument(
            name = request.name,
            description = request.description,
            price = request.price,
            type = request.type,
            mageId = request.mageId,
            monsterBonuses = request.monsterBonuses,
            available = true,
        )

        return itemMapper.toDto(itemRepository.save(item))
    }

    override fun deleteItem(request: DeleteItemRequest) {
        itemRepository.deleteById(request.id)
    }

    override fun getItemById(itemId: String): ItemResponse =
        itemMapper.toDto(itemRepository.findById(itemId).orElseThrow { IllegalArgumentException("Item not found") })

    override fun getAvailableItemsByMage(mageId: Long): List<ItemDocument> =
        itemRepository.findByMageIdAndAvailableTrue(mageId)

    override fun setAvailability(itemId: String, available: Boolean) {
        val item = itemRepository.findById(itemId).orElseThrow { IllegalArgumentException("Item not found") }
        item.available = available
        itemRepository.save(item)
    }
    override fun getItemsByWitcherId(witcherId: Long): List<ItemDocument> {
        val inventory = inventoryRepository
            .findByWitcherId(witcherId)
            ?: return emptyList()

        return itemRepository
            .findAllById(inventory.itemIds)
    }
}
