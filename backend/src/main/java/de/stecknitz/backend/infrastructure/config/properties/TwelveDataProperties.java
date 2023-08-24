package de.stecknitz.backend.infrastructure.config.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;


@Getter
@Setter
@Validated
public class TwelveDataProperties {

   @NotNull
   private String baseUrl;

   @NotNull
   private String apiKey;

}
