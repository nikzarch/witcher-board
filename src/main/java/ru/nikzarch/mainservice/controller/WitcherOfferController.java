package ru.nikzarch.mainservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nikzarch.mainservice.domain.order.OrderStatus;
import ru.nikzarch.mainservice.domain.witcheroffer.WitcherOffer;
import ru.nikzarch.mainservice.domain.witcheroffer.WitcherOfferStatus;
import ru.nikzarch.mainservice.repository.WitchOfferRepository;
import ru.nikzarch.mainservice.repository.OrderRepository;
import ru.nikzarch.mainservice.service.impl.NotificationServiceImpl;
import ru.nikzarch.witcherboard.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/offers")
@RequiredArgsConstructor
public class WitcherOfferController {

    private final WitchOfferRepository offerRepository;
    private final OrderRepository orderRepository;
    private final NotificationServiceImpl notificationService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<String> createOffer(
            @RequestParam Long orderId,
            @RequestParam Long suggestedPrice,
            @RequestParam Long witcherId
    ) {
        WitcherOffer offer = new WitcherOffer();
        offer.setOrder(orderRepository.findById(orderId).orElseThrow());
        offer.setSuggestedPrice(suggestedPrice);
        offer.setOrderStatus(WitcherOfferStatus.CREATED);
        offer.setWitcherId(userRepository.getReferenceById(witcherId));
        offerRepository.save(offer);
        return ResponseEntity.ok("Заказ с id " + offer.getId() + " успешно создан!");
    }

    @PutMapping("/{offerId}")
    public ResponseEntity<WitcherOffer> acceptOffer(@PathVariable Long offerId) {
        WitcherOffer offer = offerRepository.findById(offerId).orElseThrow();
        offer.setOrderStatus(WitcherOfferStatus.ACCEPTED);
        var order = offer.getOrder();
        order.setOrderStatus(OrderStatus.ACTIVE);
        orderRepository.save(order);
        notificationService.createNotification(offer.getWitcherId(), "Кривозубый крестьянин принял вашу цену" );
        return ResponseEntity.ok(offerRepository.save(offer));
    }

    @PostMapping("/{offerId}/reject")
    public ResponseEntity<WitcherOffer> rejectOffer(@PathVariable Long offerId) {
        WitcherOffer offer = offerRepository.findById(offerId).orElseThrow();
        offer.setOrderStatus(WitcherOfferStatus.REJECTED);
        notificationService.createNotification(offer.getWitcherId(), "Кривозубый крестьянин отказался от заказа" );
        return ResponseEntity.ok(offerRepository.save(offer));
    }
}
