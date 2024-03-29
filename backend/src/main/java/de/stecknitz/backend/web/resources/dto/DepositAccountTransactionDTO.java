package de.stecknitz.backend.web.resources.dto;

import de.stecknitz.backend.core.domain.DepositAccountTransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class DepositAccountTransactionDTO {

    private long id;

    private long amount;

    private DepositAccountTransactionType type;

    private String created_at;

}
