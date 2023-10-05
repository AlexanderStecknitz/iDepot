package de.stecknitz.backend.core.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

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
    @NotNull
    @Column(name = "buy_price")
    private float buyPrice;
    @CreatedDate
    @Column(name = "created")
    private Instant created;

    public double getInvestmentValue() {
        return buyPrice * amount;
    }

    public double calculateInvestmentValue(double accumulatedInvestmentValue) {
        return (getInvestmentValue() / accumulatedInvestmentValue) * 100;
    }

    public float calculateYield() {
        return ((stock.getCurrentPrice() - buyPrice) / buyPrice) * 100;
    }
}
