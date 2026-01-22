package ru.nikzarch.witcherboard.mongo.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.nikzarch.witcherboard.mongo.dto.request.CreateItemRequest
import ru.nikzarch.witcherboard.mongo.dto.response.ItemResponse
import ru.nikzarch.witcherboard.mongo.service.ItemService

@RestController
@RequestMapping("/api/v1/items")
class ItemController(
    private val itemService: ItemService
) {
    @PreAuthorize("hasAnyRole('GOD', 'MAGE')")
    @PostMapping
    fun createItem(
        @RequestBody request: CreateItemRequest
    ): ResponseEntity<ItemResponse> =
        ResponseEntity.status(HttpStatus.CREATED)
            .body(itemService.createItem(request))

    @GetMapping("/{id}")
    fun getItem(
        @PathVariable id: String
    ): ResponseEntity<ItemResponse> =
        ResponseEntity.ok(itemService.getItemById(id))

    @PreAuthorize("hasAnyRole('GOD','MAGE')")
    @DeleteMapping("/{id}")
    fun deleteItem(@PathVariable id: String) = ResponseEntity.ok(itemService.deleteItem(id))

    @GetMapping
    fun getAllItems() : ResponseEntity<List<ItemResponse>> = ResponseEntity.ok(itemService.getAll())

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity.badRequest().body(ex.message)
}
