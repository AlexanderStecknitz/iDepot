package de.stecknitz.backend.web.resources.dto;

import lombok.Data;

@Data
public class ShareDTO {
    String isin;
    String wkn;
    int amount;
    float price;
}
