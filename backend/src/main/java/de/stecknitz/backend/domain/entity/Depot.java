package de.stecknitz.backend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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
}
