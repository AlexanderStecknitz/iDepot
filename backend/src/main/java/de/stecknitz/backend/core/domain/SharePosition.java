package de.stecknitz.backend.core.domain;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "share_position_seq",
            sequenceName = "share_position_share_position_id_seq")
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
