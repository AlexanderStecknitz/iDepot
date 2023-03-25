package de.stecknitz.backend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "share")
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Share {

    @Id
    @Column(name = "isin")
    private String isin;
    @Column(name = "wkn")
    private String wkn;
    @Column(name = "amount")
    private int amount;
    @Column(name = "price")
    private float price;

}
