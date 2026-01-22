package ru.nikzarch.mainservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nikzarch.mainservice.domain.witcheroffer.WitcherOffer;
import ru.nikzarch.mainservice.domain.witcheroffer.WitcherOfferStatus;
import ru.nikzarch.mainservice.repository.OrderRepository;
import ru.nikzarch.mainservice.repository.WitchOfferRepository;
import ru.nikzarch.mainservice.service.WitcherOfferService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WitcherOfferServiceImpl implements WitcherOfferService {

    private final WitchOfferRepository offerRepository;
    private final OrderRepository orderRepository;

    @Override
    public WitcherOffer createOffer(Long orderId, Long suggestedPrice) {
        WitcherOffer offer = new WitcherOffer();
        offer.setOrder(orderRepository.findById(orderId).orElseThrow());
        offer.setSuggestedPrice(suggestedPrice);
        offer.setOrderStatus(WitcherOfferStatus.CREATED);

        return offerRepository.save(offer);
    }

    @Override
    public WitcherOffer updateStatus(Long offerId, WitcherOfferStatus status) {
        WitcherOffer offer = offerRepository.findById(offerId).orElseThrow();

        if (offer.getOrderStatus() != WitcherOfferStatus.CREATED) {
            throw new IllegalStateException("Offer status already finalized");
        }
        var order = offer.getOrder();
        order.setReward(Math.toIntExact(offer.getSuggestedPrice()));
        offer.setOrderStatus(status);
        orderRepository.save(order);
        return offerRepository.save(offer);
    }
    @Override
    public List<WitcherOffer> getOffersByOrder(Long orderId) {
        return offerRepository.findByOrderId(orderId);
    }

}
