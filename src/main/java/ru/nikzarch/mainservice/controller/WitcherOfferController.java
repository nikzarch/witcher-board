package ru.nikzarch.mainservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.nikzarch.mainservice.domain.witcheroffer.WitcherOffer;
import ru.nikzarch.mainservice.domain.witcheroffer.WitcherOfferStatus;
import ru.nikzarch.mainservice.service.WitcherOfferService;

@RestController
@RequestMapping("/api/v1/offers")
@RequiredArgsConstructor
public class WitcherOfferController {

    private final WitcherOfferService witcherOfferService;

    @PreAuthorize("hasRole('WITCHER')")
    @PostMapping
    public ResponseEntity<WitcherOffer> createOffer(
            @RequestParam Long orderId,
            @RequestParam Long suggestedPrice
    ) {
        return ResponseEntity.ok(
                witcherOfferService.createOffer(orderId, suggestedPrice)
        );
    }

    @PreAuthorize("hasRole('PEASANT')")
    @PutMapping("/{offerId}/status")
    public ResponseEntity<WitcherOffer> updateOfferStatus(
            @PathVariable Long offerId,
            @RequestParam WitcherOfferStatus status
    ) {
        return ResponseEntity.ok(
                witcherOfferService.updateStatus(offerId, status)
        );
    }
}
