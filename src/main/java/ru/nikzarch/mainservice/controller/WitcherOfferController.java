package ru.nikzarch.mainservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nikzarch.mainservice.domain.witcheroffer.WitcherOffer;
import ru.nikzarch.mainservice.domain.witcheroffer.WitcherOfferStatus;
import ru.nikzarch.mainservice.repository.WitchOfferRepository;
import ru.nikzarch.mainservice.repository.OrderRepository;

@RestController
@RequestMapping("/api/v1/offers")
@RequiredArgsConstructor
public class WitcherOfferController {

    private final WitchOfferRepository offerRepository;
    private final OrderRepository orderRepository;

    /** Ведьмак предлагает другую цену */
    @PostMapping
    public ResponseEntity<WitcherOffer> createOffer(
            @RequestParam Long orderId,
            @RequestParam Long suggestedPrice
    ) {
        WitcherOffer offer = new WitcherOffer();
        offer.setOrder(orderRepository.findById(orderId).orElseThrow());
        offer.setSuggestedPrice(suggestedPrice);
        offer.setOrderStatus(WitcherOfferStatus.CREATED);

        return ResponseEntity.ok(offerRepository.save(offer));
    }

    /** Крестьянин принимает предложение */
    @PutMapping("/{offerId}")
    public ResponseEntity<WitcherOffer> acceptOffer(@PathVariable Long offerId) {
        WitcherOffer offer = offerRepository.findById(offerId).orElseThrow();
        offer.setOrderStatus(WitcherOfferStatus.ACCEPTED);
        return ResponseEntity.ok(offerRepository.save(offer));
    }

    /** Крестьянин отклоняет */
    @PostMapping("/{offerId}/reject")
    public ResponseEntity<WitcherOffer> rejectOffer(@PathVariable Long offerId) {
        WitcherOffer offer = offerRepository.findById(offerId).orElseThrow();
        offer.setOrderStatus(WitcherOfferStatus.REJECTED);
        return ResponseEntity.ok(offerRepository.save(offer));
    }
}
