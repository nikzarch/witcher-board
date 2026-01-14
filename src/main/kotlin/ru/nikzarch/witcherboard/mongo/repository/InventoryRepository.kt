package ru.nikzarch.witcherboard.mongo.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ru.nikzarch.witcherboard.mongo.document.InventoryDocument

@Repository
interface InventoryRepository : MongoRepository<InventoryDocument, String> {

    fun findByWitcherId(witcherId: Long): InventoryDocument?
}
