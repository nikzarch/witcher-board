package ru.sinchi.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sinchi.mainservice.domain.witcheroffer.WitcherOffer;

@Repository
public interface WitchOfferRepository extends JpaRepository<WitcherOffer, Long> {
}
