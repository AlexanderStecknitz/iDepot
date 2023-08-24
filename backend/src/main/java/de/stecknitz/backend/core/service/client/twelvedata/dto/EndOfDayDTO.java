package de.stecknitz.backend.core.service.client.twelvedata.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class EndOfDayDTO {
    private String symbol;
    private String exchange;
    private String mic_code;
    private String currency;
    private String datetime;
    private String close;
}
