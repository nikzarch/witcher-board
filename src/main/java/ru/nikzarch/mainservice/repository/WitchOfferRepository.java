package ru.nikzarch.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nikzarch.mainservice.domain.witcheroffer.WitcherOffer;

@Repository
public interface WitchOfferRepository extends JpaRepository<WitcherOffer, Long> {
}
