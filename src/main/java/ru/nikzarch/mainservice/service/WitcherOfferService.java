package ru.nikzarch.mainservice.service;

import ru.nikzarch.mainservice.domain.witcheroffer.WitcherOffer;
import ru.nikzarch.mainservice.domain.witcheroffer.WitcherOfferStatus;

public interface WitcherOfferService {

    WitcherOffer createOffer(Long orderId, Long suggestedPrice);

    WitcherOffer updateStatus(Long offerId, WitcherOfferStatus status);
}
