package ru.nikzarch.witcherboard.mongo.service.impl

import org.springframework.stereotype.Service
import ru.nikzarch.witcherboard.mongo.document.InventoryDocument
import ru.nikzarch.witcherboard.mongo.exception.ItemNotFoundException
import ru.nikzarch.witcherboard.mongo.repository.InventoryRepository
import ru.nikzarch.witcherboard.mongo.service.InventoryService

@Service
class InventoryServiceImpl(
    private val inventoryRepository: InventoryRepository,
) : InventoryService {
    override fun addItem(witcherId: Long, itemId: String): InventoryDocument {
        val inventory = inventoryRepository.findByWitcherId(witcherId)
            ?: inventoryRepository.save(
                InventoryDocument(witcherId = witcherId)
            )

        inventory.itemIds.add(itemId)
        return inventoryRepository.save(inventory)
    }

    override fun removeItem(witcherId: Long, itemId: String) {
        val inventory = inventoryRepository.findByWitcherId(witcherId)
            ?: run {
                throw ItemNotFoundException("item not found cause inventory is empty")
            }
        inventory.itemIds.remove(itemId)
        inventoryRepository.save(inventory)
    }

    override fun getByWitcherId(witcherId: Long): InventoryDocument {
        val inventory = inventoryRepository.findByWitcherId(witcherId)
            ?: inventoryRepository.save(
                InventoryDocument(witcherId = witcherId)
            )
        return inventoryRepository.save(inventory)
    }

}