package de.stecknitz.backend.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "share_position")
public class SharePosition {

    @Id
    private long sharePositionId;
    @ManyToOne
    @JoinColumn(name = "depot_id")
    private Depot depot;
    @ManyToOne
    @JoinColumn(name = "share_isin")
    private Share share;
    @Column(name = "amount")
    private float amount;
    @Column(name = "buy_price")
    private float buyPrice;
}
