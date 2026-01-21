package ru.nikzarch.witcherboard.mongo.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.nikzarch.witcherboard.mongo.document.InventoryDocument
import ru.nikzarch.witcherboard.mongo.service.InventoryService
import ru.nikzarch.witcherboard.service.SecurityService
import ru.nikzarch.witcherboard.service.UserService

@RestController
@RequestMapping("/api/v1/inventories")
class InventoryController(
    private val inventoryService: InventoryService,
    private val securityService: SecurityService,
    private val userService: UserService
) {

    @PreAuthorize("hasAnyRole('GOD', 'WITCHER') and @securityService.isOwner(#witcherId)")
    @GetMapping("/{witcherId}")
    fun getInventory(
        @PathVariable witcherId: Long
    ): ResponseEntity<InventoryDocument> =
        ResponseEntity.ok(inventoryService.getByWitcherId(witcherId))

    @PreAuthorize("hasAnyRole('GOD','WITCHER')")
    @GetMapping("/my")
    fun getMyInventory() : ResponseEntity<InventoryDocument> =
        ResponseEntity.ok(inventoryService.getByWitcherId(userService.findUserByName(securityService.getAuthenticatedUser().username)?.id!!))

    @PreAuthorize("hasAnyRole('GOD', 'MAGE') ")
    @PostMapping("/{witcherId}/items/{itemId}")
    fun addItem(
        @PathVariable witcherId: Long,
        @PathVariable itemId: String
    ): ResponseEntity<InventoryDocument> =
        ResponseEntity.ok(inventoryService.addItem(witcherId, itemId))

    @PreAuthorize("hasAnyRole('GOD', 'WITCHER') and @securityService.isOwner(#witcherId)")
    @DeleteMapping("/{witcherId}/items/{itemId}")
    fun removeItem(
        @PathVariable witcherId: Long,
        @PathVariable itemId: String
    ): ResponseEntity<Unit> =
        ResponseEntity.ok(inventoryService.removeItem(witcherId, itemId))


    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalState(ex: IllegalStateException): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.CONFLICT).body(ex.message)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity.badRequest().body(ex.message)
}
