package de.stecknitz.backend.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "investment")
@EqualsAndHashCode
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "investment_id")
    private long investmentId;
    @ManyToOne
    @JoinColumn(name = "depot_id")
    private Depot depot;
    @ManyToOne
    @JoinColumn(name = "stock_isin")
    private Stock stock;
    @Column(name = "amount")
    private float amount;
    @Column(name = "buy_price")
    private float buyPrice;
}
