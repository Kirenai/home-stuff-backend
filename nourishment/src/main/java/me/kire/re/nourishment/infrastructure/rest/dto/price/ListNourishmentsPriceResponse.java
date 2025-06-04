package me.kire.re.nourishment.infrastructure.rest.dto.price;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ListNourishmentsPriceResponse(
        @JsonProperty("nourishmentPriceId")
        String nourishmentPriceId,
        @JsonProperty("price")
        BigDecimal price,
        @JsonFormat(pattern = "yyyy-MM-dd")
        @JsonProperty("timestamp")
        LocalDate timestamp
) {
}
