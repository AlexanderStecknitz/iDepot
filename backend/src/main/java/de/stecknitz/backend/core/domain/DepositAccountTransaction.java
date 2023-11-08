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
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

@Entity
@Table(name = "deposit_account_transaction")
@Builder
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

    @CreatedDate
    private Timestamp created_at;

}
