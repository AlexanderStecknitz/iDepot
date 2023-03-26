package de.stecknitz.backend.core.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @Column(name = "name")
    private String name;
    @Column(name = "amount")
    private int amount;
    @Column(name = "price")
    private float price;

    @ManyToMany(mappedBy = "shares")
    private List<Depot> depots;

}
