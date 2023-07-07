package de.stecknitz.backend.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "stock")
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Stock {

    @Id
    @Column(name = "isin")
    private String isin;
    @Column(name = "wkn")
    private String wkn;
    @Column(name = "name")
    private String name;
    @Column(name = "actual_price")
    private float actualPrice;

}
