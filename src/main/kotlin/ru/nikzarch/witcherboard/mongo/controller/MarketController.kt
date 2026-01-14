package ru.nikzarch.witcherboard.mongo.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.nikzarch.witcherboard.mongo.service.MarketService

@RestController
@RequestMapping("/api/v1/market")
class MarketController(
    private val marketService: MarketService
) {

    @PreAuthorize("hasAnyRole('GOD', 'WITCHER') and @securityService.isOwner(#witcherId)")
    @PostMapping("/buy")
    fun buyItem(
        @RequestParam witcherId: Long,
        @RequestParam itemId: String
    ): ResponseEntity<String> {
        marketService.buyItem(witcherId, itemId)
        return ResponseEntity.ok("Item purchased")
    }

    @PreAuthorize("hasAnyRole('GOD', 'WITCHER') and @securityService.isOwner(#witcherId)")
    @PostMapping("/sell")
    fun sellItem(
        @RequestParam witcherId: Long,
        @RequestParam itemId: String
    ): ResponseEntity<String> {
        marketService.sellItem(witcherId, itemId)
        return ResponseEntity.ok("Item sold")
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalState(ex: IllegalStateException): ResponseEntity<String> =
        ResponseEntity.badRequest().body(ex.message)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity.badRequest().body(ex.message)
}
