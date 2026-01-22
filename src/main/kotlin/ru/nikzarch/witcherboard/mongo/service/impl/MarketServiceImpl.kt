package ru.nikzarch.witcherboard.mongo.service.impl

import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.nikzarch.witcherboard.mongo.exception.ItemNotFoundException
import ru.nikzarch.witcherboard.mongo.exception.ItemUnavailableException
import ru.nikzarch.witcherboard.mongo.repository.InventoryRepository
import ru.nikzarch.witcherboard.mongo.repository.ItemRepository
import ru.nikzarch.witcherboard.mongo.service.InventoryService
import ru.nikzarch.witcherboard.mongo.service.MarketService
import ru.nikzarch.witcherboard.service.UserService

@Service
class MarketServiceImpl(
    private val inventoryRepository: InventoryRepository,
    private val itemRepository: ItemRepository,
    private val userService: UserService,
    private val inventoryService: InventoryService
) : MarketService {

    @Transactional
    override fun buyItem(witcherId: Long, itemId: String) {
        val item = itemRepository.findById(itemId)
            .orElseThrow { ItemNotFoundException("item not found") }

        if (!item.available) {
            throw ItemUnavailableException("item is unavailable")
        }

        userService.changeBalance(witcherId, -item.price.toLong())

        userService.changeBalance(item.mageId, item.price.toLong())

        inventoryService.addItem(witcherId, item.id!!)
        item.available = false
        itemRepository.save(item)
    }

    @Transactional
    override fun sellItem(witcherId: Long, itemId: String) {
        val item = itemRepository.findById(itemId).orElseThrow { ItemNotFoundException("item not found") }

        inventoryService.removeItem(witcherId, itemId)

        userService.changeBalance(witcherId, item.price.toLong())
        userService.changeBalance(item.mageId, -item.price.toLong())

        item.available = true
        itemRepository.save(item)
    }

    override fun getBalanceByUserId(userId: Long) : Long {
        val user = userService.findUserById(userId)
            ?: throw UsernameNotFoundException("user not found")
        return user.balance
    }

    override fun getBalanceByUsername(username: String): Long {
        val user = userService.findUserByName(username)
            ?: throw UsernameNotFoundException("user not found")
        return user.balance;
    }

}
