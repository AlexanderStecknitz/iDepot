package de.stecknitz.backend.core.domain;

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
            name = "depot_share",
            joinColumns = @JoinColumn(name = "depot_id"),
            inverseJoinColumns = @JoinColumn(name = "share_isin"))
    private List<Share> shares;

}
