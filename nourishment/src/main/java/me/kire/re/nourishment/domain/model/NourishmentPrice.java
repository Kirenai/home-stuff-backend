package me.kire.re.nourishment.domain.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record NourishmentPrice(
        String nourishmentPriceId,
        BigDecimal price,
        LocalDate timestamp,
        String nourishmentId
) {
}
