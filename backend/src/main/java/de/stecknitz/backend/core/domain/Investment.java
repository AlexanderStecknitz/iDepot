package de.stecknitz.backend.core.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "investment")
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "investment_id")
    private long investmentId;
    @ManyToOne
    @JsonBackReference
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
