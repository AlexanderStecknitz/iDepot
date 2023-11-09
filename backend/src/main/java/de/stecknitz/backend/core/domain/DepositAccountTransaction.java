package de.stecknitz.backend.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Table(name = "deposit_account_transaction")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DepositAccountTransaction {
    @Id
    private long id;

    @ManyToOne
    private DepositAccount depositAccount;

    @Column(name = "amount", nullable = false)
    private long amount;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DepositAccountTransactionType type;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

}
