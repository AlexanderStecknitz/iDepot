package de.stecknitz.backend.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
