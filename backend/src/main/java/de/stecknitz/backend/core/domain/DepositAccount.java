package de.stecknitz.backend.core.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "deposit_account")
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class DepositAccount {

    @Id
    private int id;

    @OneToOne
    private Depot depot;

}
