package ru.nikzarch.mainservice.domain.witcheroffer;

import jakarta.persistence.*;
import lombok.Data;
import ru.nikzarch.witcherboard.domain.user.User;
import ru.nikzarch.mainservice.domain.order.Order;

@Entity
@Table(name = "witcher_offer")
@Data
public class WitcherOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    Order order;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    User witcherId;

    @Column(name = "suggested_price")
    Long suggestedPrice;

    @Column(name = "witcher_offer_status")
    @Enumerated(EnumType.STRING)
    WitcherOfferStatus orderStatus;
}
