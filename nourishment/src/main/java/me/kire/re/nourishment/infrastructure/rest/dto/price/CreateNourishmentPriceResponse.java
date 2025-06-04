package me.kire.re.nourishment.infrastructure.rest.dto.price;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateNourishmentPriceResponse(
        String nourishmentPriceId,
        BigDecimal price,
        LocalDate timestamp
) {
}
