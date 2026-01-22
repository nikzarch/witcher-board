package ru.nikzarch.witcherboard.mongo.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.nikzarch.witcherboard.mongo.service.MarketService
import ru.nikzarch.witcherboard.service.SecurityService
import ru.nikzarch.witcherboard.service.UserService

@RestController
@RequestMapping("/api/v1/market")
class MarketController(
    private val marketService: MarketService,
    private val securityService: SecurityService,
    private val userService: UserService
) {

    @PreAuthorize("hasAnyRole('GOD', 'WITCHER')")
    @PostMapping("/buy")
    fun buyItem(
        @RequestParam witcherId: Long,
        @RequestParam itemId: String
    ): ResponseEntity<String> {
        marketService.buyItem(witcherId, itemId)
        return ResponseEntity.ok("Item purchased")
    }

    @PreAuthorize("hasAnyRole('GOD', 'WITCHER')")
    @PostMapping("/sell")
    fun sellItem(
        @RequestParam witcherId: Long,
        @RequestParam itemId: String
    ): ResponseEntity<String> {
        marketService.sellItem(witcherId, itemId)
        return ResponseEntity.ok("Item sold")
    }

    @GetMapping("/my-balance")
    fun getMyBalance() : ResponseEntity<Long> = ResponseEntity.ok(marketService.getBalanceByUsername(securityService.getAuthenticatedUser().username))

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalState(ex: IllegalStateException): ResponseEntity<String> =
        ResponseEntity.badRequest().body(ex.message)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity.badRequest().body(ex.message)
}
