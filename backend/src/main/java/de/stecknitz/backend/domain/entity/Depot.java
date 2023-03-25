package de.stecknitz.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "depot")
@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Depot {
    @Id
    @Column(name = "id")
    private long id;

    @ManyToMany
    @JoinTable(
            name = "depot_shares",
            joinColumns = @JoinColumn(name = "share_isin"),
            inverseJoinColumns = @JoinColumn(name = "depot_id"))
    private List<Share> shares;
}